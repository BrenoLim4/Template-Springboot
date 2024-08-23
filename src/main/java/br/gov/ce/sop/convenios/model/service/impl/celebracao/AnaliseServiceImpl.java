package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.AnaliseXDocumento;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDocumentosPrefeito;
import br.gov.ce.sop.convenios.model.enums.StatusAnalise;
import br.gov.ce.sop.convenios.model.enums.StatusCelebracao;
import br.gov.ce.sop.convenios.model.enums.StatusConferenciaDocumento;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import br.gov.ce.sop.convenios.model.repository.celebracao.AnaliseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseXDocumentoService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.HistoricoAnaliseService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class AnaliseServiceImpl implements AnaliseService {

    private final AnaliseRepository repository;
    private final AnaliseXDocumentoService analiseXDocumentoService;
    private final HistoricoAnaliseService historicoAnaliseService;
    private final PrefeitoService prefeitoService;

    @Override
    public List<Analise> getEntities(Analise filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Analise> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<Analise> getAll() {
        return repository.findAll();
    }

    @Override
    public Analise getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void gerarAnalisesPosEnvio(Integer idCelebracao) {
        /*
         * Criar duas analises Administrativo e engenharia - OK
         * Gravar histórico - OK
         * Dividir os documentos para analises Administrativo e engenharia
         * */
        Analise analiseAdministrativa = TipoAnalise.ADMINISTRATIVA.createAnalise(idCelebracao);
        Analise analiseEngenharia = TipoAnalise.ENGENHARIA.createAnalise(idCelebracao);
        List<Analise> analises = repository.saveAll(asList(analiseEngenharia, analiseAdministrativa));
        // a tabela analise_x_documentos é preenchida na trigger, após o insert. Dividindo os documentos para cada analise
        analises.forEach(historicoAnaliseService::salvarHistoricoGeracao);
    }

    @Override
    public void iniciarAnalise(AnaliseXDocumento analiseXDocumento) {
        if (analiseXDocumentoService.primeiroDocumentoConferido(analiseXDocumento)) {
            Analise analise = analiseXDocumento.getAnalise();
            analise.setStatusAnalise(StatusAnalise.PARCIALMENTE_CONFERIDA);
            repository.save(analise);
            historicoAnaliseService.salvarHistoricoAnaliseIniciada(analise);
        }
    }

    @Override
    @Transactional
    public void conferirDocumento(Integer idDocumento, Integer idAnalise) {
        AnaliseXDocumento analiseXDocumento = analiseXDocumentoService.conferirDocumento(idDocumento, idAnalise);
        iniciarAnalise(analiseXDocumento);
    }

    @Override
    @Transactional
    public void rejeitarDocumento(Integer idDocumento, String comentario, Integer idAnalise) {
        AnaliseXDocumento analiseXDocumento = analiseXDocumentoService.rejeitarDocumento(idDocumento, comentario, idAnalise);
        iniciarAnalise(analiseXDocumento);
    }

    @Override
    @Transactional
    public String atribuirEngenheiro(String matricula, Integer idCelebracao) {
        Analise analise = repository.findByCelebracao_IdAndTipoAndAtualTrue(idCelebracao, TipoAnalise.ENGENHARIA).orElseThrow(() -> new RuntimeException("Análise não encontrada"));
        String matriculaEngenheiro = analise.getMatriculaEngenheiro();
        if (Objects.nonNull(matriculaEngenheiro) ){
            if (matriculaEngenheiro.equals(matricula)){//significa que é o mesmo engenheiro
                return "Engenheiro já encontra-se atribuído para esta Celebração.";
            }else{//outro engenheiro
                analise.setMatriculaEngenheiro(matricula);
                repository.save(analise);
                historicoAnaliseService.salvarHistoricoEngenheiroSubstituido(analise);
                return "Engenheiro substituído com sucesso.";
            }
        }
        analise.setMatriculaEngenheiro(matricula);
        analise.setStatusAnalise(StatusAnalise.AGUARDANDO_INICIO);
        repository.save(analise);
        historicoAnaliseService.salvarHistoricoEngenheiroAtribuido(analise);
        return "Engenheiro atribuído com sucesso!";
    }


    @Override
    public void aprovar(Analise analise) {
        if (analise.getTipo().equals(TipoAnalise.ADMINISTRATIVA)) {
            List<VoDocumentosPrefeito> documentosPrefeito = prefeitoService.getDocumentosPrefeitoByIdPrefeitura(analise.getCelebracao().getConvenio().getConvenente().getIdPessoa());
            List<AnaliseXDocumento> analiseXDocumentos = documentosPrefeito.stream()
                    .map(doc -> AnaliseXDocumento.builder()
                            .idTipoDocumento(doc.getIdTipoDocumento())
                            .analise(analise)
                            .idDocumento(doc.getIdDocumento())
                            .ocorrencia("ÚNICA")
                            .statusConferencia(StatusConferenciaDocumento.CONFERIDO_PELO_USUARIO)
                            .build())
                    .toList();
            analiseXDocumentoService.saveAll(analiseXDocumentos);

        }
        analise.setStatusAnalise(StatusAnalise.APROVADA);
        repository.save(analise);
        historicoAnaliseService.salvarHistoricoAprovacao(analise);
    }

    @Override
    public void rejeitar(Analise analise) {
        analise.setAtual(false);
        analise.setStatusAnalise(StatusAnalise.REPROVADA);
        repository.save(analise);
        initNewVersionAnalise(analise);//não corrigir esse
        historicoAnaliseService.salvarHistoricoRejeicao(analise);
    }

    @Override
    public void documentoCorrigido(Integer idDocumento, Integer idAnalise, Integer idTipoDocumento, String ocorrencia) {
        analiseXDocumentoService.documentoCorrigido(idDocumento, idAnalise, idTipoDocumento, ocorrencia);
    }

    @Override
    public void correcaoDocumentosFinalizada(Integer idCelebracao) {
        validarCorrecaoDocumentos(idCelebracao);
        repository.findAllByCelebracao_IdAndStatusAnaliseAndAtualTrue(idCelebracao, StatusAnalise.AGUARDANDO_CORRECAO_DOCUMENTOS)
                .stream()
                .peek(analise -> analise.setStatusAnalise(StatusAnalise.PARCIALMENTE_CONFERIDA))
                .forEach(repository::save);
    }

    @Override
    public StatusCelebracao verificarAnaliseParalela(Analise analise) {
        Analise analiseParalela = repository.findByCelebracao_IdAndIdNotAndTipoNotAndAtualTrue(analise.getCelebracao().getId(), analise.getId(), analise.getTipo())
                .orElseThrow(() -> new RuntimeException("Analise paralela não encontrada."));
        //Verifica se a analise paralela(Engenharia ou administrativa) não está aprovada ou não está rejeitada, significa que ainda está em andamento

        if (Stream.of(StatusAnalise.AGUARDANDO_CORRECAO_DOCUMENTOS, StatusAnalise.APROVADA, StatusAnalise.REPROVADA).noneMatch(sa -> sa.equals(analiseParalela.getStatusAnalise()))) {
            return analise.getCelebracao().getStatusCelebracao();
        } else if (analise.getStatusAnalise().equals(StatusAnalise.APROVADA) && analiseParalela.getStatusAnalise().equals(StatusAnalise.APROVADA)) {
            //As duas analise estão aprovadas
            return StatusCelebracao.AGUARDANDO_PARECER_GECOV;
        } else {
            //subentende-se que pelo menos uma analise está rejeitada
            return StatusCelebracao.AGUARDANDO_CORRECAO_DOCUMENTOS;
        }
    }

    @Override
    public Analise findById(Integer idAnalise) {
        return repository.findById(idAnalise).orElseThrow();
    }

    private void initNewVersionAnalise(Analise analise) {
        //cria uma nova versão da analise e cópia os documentos analisado da versão anterior
        Analise newAnalise = analise.initNewVersion();
        repository.save(newAnalise);
        historicoAnaliseService.salvarHistoricoAnaliseIniciada(newAnalise);
        List<AnaliseXDocumento> analiseXDocumentos = analiseXDocumentoService.findAllByAnalise(analise);
        List<AnaliseXDocumento> xDocumentos = analiseXDocumentos.stream()
                .map(item ->
                        AnaliseXDocumento.builder()
                                .analise(newAnalise)
                                .idTipoDocumento(item.getIdTipoDocumento())
                                .statusConferencia(item.getStatusConferencia())
                                .idDocumento(item.getStatusConferencia().equals(StatusConferenciaDocumento.CONFERIDO_PELO_USUARIO) ? item.getIdDocumento() : null)
                                .idDocumentoRecusado(item.getStatusConferencia().equals(StatusConferenciaDocumento.REJEITADO) ? item.getIdDocumento() : null)
                                .comentario(item.getComentario())
                                .ocorrencia(item.getOcorrencia())
                                .build()
                ).toList();
        analiseXDocumentoService.saveAll(xDocumentos);
    }

    @Override
    public Optional<Analise> findByCelebracao_IdAndTipoAndAtualTrue(Integer idCelebracao, TipoAnalise tipoAnalise) {
        return repository.findByCelebracao_IdAndTipoAndAtualTrue(idCelebracao, tipoAnalise);
    }

    private void validarCorrecaoDocumentos(Integer idCelebracao) {
        /*
         * Lança exception se possuir algum documento rejeitado ou aguardando assinatura
         * */
        List<Analise> analises = repository
                .findAllByCelebracao_IdAndStatusAnaliseAndAtualTrue(idCelebracao, StatusAnalise.AGUARDANDO_CORRECAO_DOCUMENTOS);
        if(analises.stream().anyMatch(analise -> analiseXDocumentoService.possuiDocumentosReprovados(analise.getId()))){
            throw new WarningException("Necessário o reenvio de todos os documentos rejeitados antes de finalizar.");
        }
        if (analises.stream().anyMatch(analise -> analiseXDocumentoService.possuiDocumentosAguardandoAssinatura(analise.getId()))) {
            throw new WarningException("Não foi possível finalizar correção. Há documentos pendentes de assinatura!");
        }

    }
}
