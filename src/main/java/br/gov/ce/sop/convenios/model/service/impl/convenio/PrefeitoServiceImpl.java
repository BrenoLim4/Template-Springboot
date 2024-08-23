package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.api.exception.DocumentoNotFount;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.DocumentoPrefeitoSituacaoRejeitado;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeitura;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDocumentosPrefeito;
import br.gov.ce.sop.convenios.model.enums.StatusConferenciaDocumento;
import br.gov.ce.sop.convenios.model.enums.TipoColaborador;
import br.gov.ce.sop.convenios.model.repository.celebracao.DocumentoPrefeitoSituacaoRejeitadoRepository;
import br.gov.ce.sop.convenios.model.repository.convenio.prefeitura.PrefeitoRepository;
import br.gov.ce.sop.convenios.model.repository.convenio.prefeitura.VoDocumentosPrefeitoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ColaboradorService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@Service
@RequiredArgsConstructor
@Log
public class PrefeitoServiceImpl implements PrefeitoService {

    private final PrefeitoRepository prefeitoRepository;
    private final ColaboradorService colaboradorService;
    private final PrefeituraService prefeituraService;
    private final VoDocumentosPrefeitoRepository documentosPrefeitoRepository;
    private final DocumentoPrefeitoSituacaoRejeitadoRepository documentoPrefeitoSituacaoRejeitadoRepository;

    private final Predicate<VoDocumentosPrefeito> possuiDocRejeitado = d -> !Objects.isNull(d.getIdStatusConferencia()) && d.getIdStatusConferencia().equals(StatusConferenciaDocumento.REJEITADO.ordinal());
    private final Predicate<VoDocumentosPrefeito> possuiDocAguardandoConferencia = d -> !Objects.isNull(d.getIdStatusConferencia()) && d.getIdStatusConferencia().equals(StatusConferenciaDocumento.AINDA_NAO_CONFERIDO.ordinal());
    private final Predicate<VoDocumentosPrefeito> possuiDocNaoEnviado = d -> !d.getEnviado();

    @Override
    @Transactional
    public void cadastrarPrefeito(ColaboradorDTO colaboradorDTO) {
        Prefeito prefeito = new Prefeito();
        BeanUtils.copyProperties(colaboradorDTO, prefeito);
        Prefeitura prefeitura = prefeituraService.findPrefeituraLogada();
        prefeito.setPrefeitura(new Prefeitura(prefeitura.getIdPessoa()));
        //desativar o prefeito anterior
        inativarPrefeito();
        prefeitoRepository.save(prefeito);
        colaboradorService.cadastrarColaborador(colaboradorDTO, TipoColaborador.PREFEITO);
    }

    private void inativarPrefeito() {
        Prefeitura prefeitura = prefeituraService.findPrefeituraLogada();
        prefeitoRepository.findByPrefeitura_IdPessoaAndAtivoIsTrue(prefeitura.getIdPessoa())
                .ifPresent(prefeito -> {
                    prefeitoRepository.desativarPrefeitoAnterior(prefeitura.getIdPessoa());
                    colaboradorService.findByIdPrefeituraAndCpf(prefeitura.getIdPessoa(), prefeito.getCpf())
                            .ifPresent(prefeitoAntigo -> colaboradorService.inativarColaborador(prefeitoAntigo.getId()));
                });
    }

    @Override
    public void atualizarDados(ColaboradorDTO colaboradorDTO) {
        Prefeito prefeito = findPrefeitoAtivoByPrefeitura();
        prefeito.setNome(colaboradorDTO.nome());
        prefeito.setEmail(colaboradorDTO.email());
        prefeitoRepository.save(prefeito);
        colaboradorService.editarColaborador(colaboradorDTO);
    }

    @Override
    public boolean possuiDocumentosAguardandoConferencia(String cnpj) {
        return getDocumentosPrefeitoByCnpjPrefeitura(cnpj).stream().anyMatch(possuiDocAguardandoConferencia);
    }

    private boolean possuiPendenciasDocumentos(List<VoDocumentosPrefeito> documentosPrefeito) {
        try {
            return documentosPrefeito.stream().anyMatch(possuiDocRejeitado);
        } catch (RuntimeException ex) {
            return false;
        }
    }

    @Override
    public boolean possuiDocumentosRejeitados() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.toString().equals("CONVENIO.CONVENENTE"))) {
            return possuiPendenciasDocumentos(getDocumentosPrefeitoByPrefeituraLogada());
        }
        return false;
    }

    @Override
    public boolean possuiDocumentosRejeitadosByCnpj(String cnpj) {
        return possuiPendenciasDocumentos(getDocumentosPrefeitoByCnpjPrefeitura(cnpj));
    }

    @Override
    public boolean existeAlgumaPendenciaDocumentos(String cnpj) {
        //Verifica se tem algum documento aguardando conferência, rejeitado ou não enviado
        return getDocumentosPrefeitoByCnpjPrefeitura(cnpj).stream().anyMatch(possuiDocAguardandoConferencia.or(possuiDocRejeitado).or(possuiDocNaoEnviado));
    }

    @Override
    public boolean possuiDocumentosAguardandoEnvio(String cnpj) {
        return getDocumentosPrefeitoByCnpjPrefeitura(cnpj).stream().anyMatch(possuiDocNaoEnviado);
    }

    @Override
    public Prefeito findPrefeitoAtivoByPrefeitura() {
        return prefeitoRepository.findByPrefeitura_IdPessoaAndAtivoIsTrue(prefeituraService.getIdPessoa()).orElseThrow(() -> new RuntimeException("Prefeito não encontrado, é necessário realizar o cadastro do prefeito."));
    }

    @Override
    public Prefeito findByCpfAndCnpjPrefeitura(String cpf, String cnpjPrefeitura) {
        return prefeitoRepository.findPrefeitoByCpfAndPrefeitura_PessoaJuridica_Cnpj(cpf, cnpjPrefeitura).orElseThrow();
    }

    @Override
    public List<VoDocumentosPrefeito> getDocumentosPrefeitoByPrefeituraLogada() {
        Prefeito prefeito = findPrefeitoAtivoByPrefeitura();
        return documentosPrefeitoRepository.findAllByCpfAndCnpj(prefeito.getCpf(), prefeito.getPrefeitura().getPessoaJuridica().getCnpj());
    }

    @Override
    public List<VoDocumentosPrefeito> getDocumentosPrefeitoByIdPrefeitura(Integer idPrefeitura) {
        return documentosPrefeitoRepository.findAllByIdPrefeitura(idPrefeitura);
    }

    @Override
    public List<VoDocumentosPrefeito> getDocumentosPrefeitoByCnpjPrefeitura(String cnpj) {
        return documentosPrefeitoRepository.findAllByCnpj(cnpj);
    }

    @Override
    public void rejeitarDocumento(Integer idDocumento, String comentario) {
        VoDocumentosPrefeito documentoPrefeito = documentosPrefeitoRepository.findVoDocumentosPrefeitosByIdDocumento(idDocumento).orElseThrow(DocumentoNotFount::new);
        Prefeito prefeito = prefeitoRepository.findById(documentoPrefeito.getIdEntidade()).get();
        documentoPrefeitoSituacaoRejeitadoRepository.findByIdDocumento(idDocumento)
                .ifPresentOrElse(documentoRejeitado -> {
                    documentoRejeitado.setComentario(comentario);
                    documentoPrefeitoSituacaoRejeitadoRepository.save(documentoRejeitado);
                }, () -> {
                    DocumentoPrefeitoSituacaoRejeitado documentoRejeitado = DocumentoPrefeitoSituacaoRejeitado.builder()
                            .idPrefeito(prefeito.getId())
                            .idDocumento(documentoPrefeito.getIdDocumento())
                            .comentario(comentario)
                            .build();
                    documentoPrefeitoSituacaoRejeitadoRepository.save(documentoRejeitado);
                });
    }

    @Override
    public void conferirDocumento(Integer idDocumento) {
        documentoPrefeitoSituacaoRejeitadoRepository.findByIdDocumento(idDocumento)
                .map(DocumentoPrefeitoSituacaoRejeitado::getId)
                .ifPresent(documentoPrefeitoSituacaoRejeitadoRepository::deleteById);
    }

    @Override
    public void checarDocumentosFinalizarEnvio() {
        if (getDocumentosPrefeitoByPrefeituraLogada().stream().anyMatch(doc -> !doc.getEnviado() || doc.getIdStatusConferencia().equals(StatusConferenciaDocumento.REJEITADO.ordinal()))) {
            throw new WarningException("Há pendências nos documentos do kit prefeito que precisam ser resolvidas. Por favor, revise e corrija os itens pendentes antes de finalizar o envio.");
        }
    }


}
