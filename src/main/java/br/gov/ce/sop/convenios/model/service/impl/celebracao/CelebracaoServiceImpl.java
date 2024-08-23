package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.api.exception.PontoControleNaoValidadoException;
import br.gov.ce.sop.convenios.api.exception.ProtocoloJaExistente;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.dto.email.AnexoDTO;
import br.gov.ce.sop.convenios.model.dto.email.BodyMail;
import br.gov.ce.sop.convenios.model.dto.email.SendMailDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDetalheResumo;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import br.gov.ce.sop.convenios.model.enums.StatusCelebracao;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoCelebracao;
import br.gov.ce.sop.convenios.model.repository.celebracao.CelebracaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.EmailService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.HistoricoCelebracaoService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoDetalheResumoService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import br.gov.ce.sop.convenios.model.service.interfaces.mapp.MappService;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.PlanoTrabalhoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
@Log
public class CelebracaoServiceImpl implements CelebracaoService {

    private final CelebracaoRepository repository;
    private final HistoricoCelebracaoService historicoService;
    private final DocumentoDigitalService documentoDigitalService;
    private final AnaliseService analiseService;
    private final PrefeitoService prefeitoService;
    private final VoCelebracaoDetalheResumoService voCelebracaoDetalheResumoService;
    private final EmailService emailService;
    private final MappService mappService;
    private final PlanoTrabalhoService planoTrabalhoService;
    @Value("${convenio.email-grupo}")
    private String GECOV_EMAIL_GRUPO;

    @Override
    public List<Celebracao> getEntities(Celebracao filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Celebracao> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<Celebracao> getAll() {
        return repository.findAll();
    }

    @Override
    public Celebracao getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Celebracao save(Celebracao celebracao) {
        return repository.save(celebracao);
    }

    @Override
    public Celebracao inserirNovaCelebracao(Convenio convenio) {
        Celebracao celebracao = Celebracao.builder().convenio(convenio).statusCelebracao(br.gov.ce.sop.convenios.model.enums.StatusCelebracao.AGUARDANDO_PROTOCOLO).build();
        celebracao = repository.save(celebracao);
        historicoService.salvar(celebracao.getId(), TipoHistoricoCelebracao.INSERIDA, "Celebração incluída");
        return celebracao;
    }

    @Override
    @Transactional
    public String finalizarEnvioDocumentos(RFinalizarEnvio params) {
        Integer idCelebracao = params.idCelebracao();
        // Checar ponto de controle
        documentoDigitalService.checarValidadePontoControle(params.idPontoEntidade());
        //Verifica se todos os documentos do kit prefeito foram enviados e não estão rejeitados
        prefeitoService.checarDocumentosFinalizarEnvio();
        // Altera status Celebração para Aguardando analise
        Celebracao celebracao = repository.findById(idCelebracao).get();
        //Altera status para Aguardando Análise
        celebracao.setStatusCelebracao(StatusCelebracao.AGUARDANDO_ANALISE_DOCUMENTOS);
        repository.save(celebracao);
        //Gravar histórico de finalização de envio de documentos
        historicoService.salvar(idCelebracao, TipoHistoricoCelebracao.ENVIOS_DOCUMENTOS_FINALIZADO, "Documentação básica enviada para análise.");
        // Gerar analises engenharia e administrativo
        analiseService.gerarAnalisesPosEnvio(idCelebracao);
        emailService.sendMailDocumentosEnviados(idCelebracao);
        return "Envio da documentação básica finalizado com sucesso e disponível para Análise";
    }

    @Override
    public String finalizarCorrecaoDocumento(Integer idCelebracao) {
        /*
         * Garantir que os documentos rejeitados tenham sido reenviados
         * Alterar status das analises, para parcialmente conferida, se tiver aguardando correção
         * Alterar status da celebração para aguardando Análise
         * Gravar histórico correção
         * */
//        analiseService.validarCorrecaoDocumentos(idCelebracao);
        analiseService.correcaoDocumentosFinalizada(idCelebracao);
        repository.findById(idCelebracao)
                .ifPresent(cel -> {
                    if (!cel.getStatusCelebracao().equals(StatusCelebracao.AGUARDANDO_ANALISE_DOCUMENTOS)) {
                        cel.setStatusCelebracao(StatusCelebracao.AGUARDANDO_ANALISE_DOCUMENTOS);
                        repository.save(cel);
                    }
                    historicoService.salvar(idCelebracao, TipoHistoricoCelebracao.ENVIO_DOCUMENTOS_REJEITADOS_FINALIZADO, "Prefeitura finalizou a correção dos documentos rejeitados.");
                });
        emailService.sendMailDocumentosCorrigidos(idCelebracao);
        return "Documentação reenviada para análise.";
    }

    @Override
    @Transactional
    public String finalizarAnalise(RFinalizarAnalise params) {
        //Verificar documentos do prefeito
        Analise analise = analiseService.findById(params.idAnalise());
        boolean isAdministrativo = TipoAnalise.ADMINISTRATIVA.equals(analise.getTipo());

        StringBuilder observacao = new StringBuilder();
        observacao.append("Análise ").append(analise.getTipo().name());
        Celebracao celebracao = analise.getCelebracao();
        try {
            //Checar ponto de controle
            documentoDigitalService.checarValidadePontoControle(params.idPontoEntidade());
            //verifica se for administrativo e se tem algum documento aguardando conferência, rejeitado ou não enviado
            if (isAdministrativo && prefeitoService.existeAlgumaPendenciaDocumentos(params.cnpjConvenente())) {
                throw new WarningException("Não é possível Finalizar análise. Documentos do prefeito possuem pendências.");
            }
            //se passar no ponto de controle, significa que todos os documentos foram aprovados
            analiseService.aprovar(analise);
            observacao.append(" aprovada!");
        } catch (PontoControleNaoValidadoException e) {
            if (documentoDigitalService.conferenciaPendente(params.idPontoEntidade())) {
                throw new WarningException("Necessário analisar todos os documentos antes de finalizar análise.");
            }
            if (!isAdministrativo && Objects.nonNull(params.documentoDiligencia())) {
                //Se for da engenharia e o documento for especificado
                documentoDigitalService.uploadDocumento(params.documentoDiligencia());
            }
            //Analise possui documentos rejeitados
            analiseService.rejeitar(analise);
            observacao.append(" reprovada!");
        }
        celebracao.setStatusCelebracao(analiseService.verificarAnaliseParalela(analise));
        repository.save(celebracao);
        historicoService.salvar(celebracao.getId(), TipoHistoricoCelebracao.ANALISE_FINALIZADA, observacao.toString());
        analise.setCelebracao(celebracao);
        emailService.sendMailCelebracaoConferida(analise);
        return "Análise finalizada com sucesso!";
    }

    @Override
    @Transactional
    public String finalizarCadastroParecer(RCadastrarParecerGecov params) {
        Celebracao celebracao = repository.findById(params.idCelebracao()).orElseThrow();
        validarCadastroParecerGecov(celebracao.getConvenio().getId(), params.idPontoEntidade());
        celebracao.setStatusCelebracao(StatusCelebracao.AGUARDANDO_PUBLICACAO);
        repository.save(celebracao);
        historicoService.salvar(celebracao.getId(), TipoHistoricoCelebracao.PARECER_GECOV_CADASTRADO, "Envio dos documentos complementares finalizado!");
        emailService.sendMailParecerCadastrado(celebracao.getId());
        return "Finalizado com sucesso ";
    }

    @Override
    public String arquivar(RParamsArquivarCelebracao params) {
        Celebracao celebracao = repository.findById(params.idCelebracao()).orElseThrow(() -> new RuntimeException("Nenhuma celebração encontrada!"));
        celebracao.setStatusCelebracao(StatusCelebracao.ARQUIVADA);
        save(celebracao);
        historicoService.salvar(celebracao.getId(), TipoHistoricoCelebracao.ARQUIVADO, params.motivo());
        return "Processo de celebração arquivado com sucesso!";
    }

    private void validarCadastroParecerGecov(Integer idConvenio, Integer idPontoEntidade) {
        documentoDigitalService.checarValidadePontoControle(idPontoEntidade);
        // verifica se o os documentos do espelho do mapp já foram enviados
        if (mappService.possuiDocumentosAguardandoEnvio(idConvenio)) {
            throw new WarningException("Necessário enviar documento do espelho do MAPP.");
        }
        // verifica se o existe plano de trabalho cadastrado
        if (!planoTrabalhoService.existsByIdConvenio(idConvenio)) {
            throw new WarningException("Necessário cadastrar Plano de Trabalho, antes de finalizar;");
        }
    }

    @Override
    public String enviarFormAberturaConta(RParamsFormAberturaConta params) {
        AnexoDTO anexo = AnexoDTO.builder().base64(params.documento()).filename("formulario-abertura-conta.pdf").build();
        VoCelebracaoDetalheResumo celebracao = voCelebracaoDetalheResumoService.getEntityById(params.idCelebracao());
        Prefeito prefeito = prefeitoService.findByCpfAndCnpjPrefeitura(celebracao.getCpfPrefeito(), celebracao.getCnpjConvenente());
        String titulo = "Formulário de abertura de conta";
        Map<String, String> details = new LinkedHashMap<>();
        details.put("Número Protocolo", celebracao.getNrProtocolo());
        details.put("Objeto", celebracao.getObjeto());
        details.put("Tipo", celebracao.getTipoConvenio());
        details.put("Mapp(s)", celebracao.getMappsIds());
        details.put("Status", celebracao.getStatus());
        BodyMail bodyMail = BodyMail.builder()
                .titulo(titulo)
                .mensagemPrincipal("Segue em anexo formulário de abertura de conta. Após a abertura da conta, deve ser anexado ao sistema de Convênio o documento preenchido e assinado pelo representante legal da CAIXA.")
                .details(details)
                .build();
        List<String> destinatarios = Objects.nonNull(params.email()) && !params.email().isBlank() ? asList(params.email(), prefeito.getEmail()) : Collections.singletonList(prefeito.getEmail());

        SendMailDTO sendMailDTO = new SendMailDTO(destinatarios, GECOV_EMAIL_GRUPO, titulo, bodyMail, new ArrayList<>(), true, Collections.singletonList(anexo));
        try {
            emailService.sendMail(sendMailDTO);
        } catch (Exception ex) {
            log.severe(ex.getMessage());
            throw new WarningException("Erro ao enviar email do formulário de abertura de conta!");
        }
        return "Email Enviado com sucesso.";
    }

    @Override
    public boolean existsByNrProtocolo(String nrProtocolo) {
        return repository.existsByNrProtocolo(nrProtocolo);
    }

    @Override
    public void celebracaoFinalizada(Integer idConvenio) {
        Celebracao celebracao = repository.findByConvenio_Id(idConvenio).orElseThrow();
        celebracao.setStatusCelebracao(StatusCelebracao.PUBLICADA);
        repository.save(celebracao);
        gravarHistoricoCelebracao(celebracao.getId(), TipoHistoricoCelebracao.PUBLICACAO_CADASTRADA, "Registrado Publicação do Convênio no Diário Oficial. Protocolo: " + celebracao.getNrProtocolo());
    }

    @Override
    public void gravarHistoricoCelebracao(Integer idCelebracao, TipoHistoricoCelebracao tipoHistorico, String observacao) {
        historicoService.salvar(idCelebracao, tipoHistorico, observacao);
    }

    @Override
    @Transactional
    public void protocolar(String nrProtocolo, LocalDateTime dataHoraProcesso, Object idEntidade) {
        if (existsByNrProtocolo(nrProtocolo)) {
            throw new ProtocoloJaExistente(nrProtocolo);
        }
        Integer idCelebracao = Integer.valueOf(idEntidade.toString());
        Celebracao celebracao = getEntityById(idCelebracao);

        celebracao.setStatusCelebracao(StatusCelebracao.AGUARDANDO_ENVIO_DOCUMENTOS);
        celebracao.setNrProtocolo(nrProtocolo);
        celebracao.setDataHoraProtocolo(dataHoraProcesso);
        save(celebracao);

        emailService.sendMailCelebracaoProtocolada(idCelebracao, nrProtocolo);

        String observacao = "Celebração protocolada, número protocolo [" + nrProtocolo + "].";
        gravarHistoricoCelebracao(idCelebracao, TipoHistoricoCelebracao.PROTOCOLADA, observacao);
    }
}
