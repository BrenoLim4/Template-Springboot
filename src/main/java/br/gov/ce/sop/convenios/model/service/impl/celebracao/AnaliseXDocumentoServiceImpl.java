package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.exception.DocumentoNotFount;
import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.AnaliseXDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsultaAnalisesDocumento;
import br.gov.ce.sop.convenios.model.enums.StatusConferenciaDocumento;
import br.gov.ce.sop.convenios.model.repository.celebracao.AnaliseXDocumentoRepository;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoConsultaAnalisesDocumentosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseXDocumentoService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnaliseXDocumentoServiceImpl implements AnaliseXDocumentoService {

    private final AnaliseXDocumentoRepository repository;
    private final DocumentoDigitalService documentoDigitalService;
    private final VoCelebracaoConsultaAnalisesDocumentosRepository voCelebracaoConsultaAnalisesDocumentosRepository;

    @Override
    public AnaliseXDocumento findByIdAnaliseAndIdDocumento(Integer idAnalise, Integer idDocumento) {
        return repository.findByAnalise_idAndIdDocumento(idAnalise, idDocumento).orElse(null);
    }

    @Override
    public List<AnaliseXDocumento> findAllByAnalise(Analise analise) {
        return repository.findAllByAnalise_Id(analise.getId());
    }

    @Override
    @Transactional
    public AnaliseXDocumento conferirDocumento(Integer idDocumento, Integer idAnalise) {
        AnaliseXDocumento analiseXDocumento = findByIdAnaliseAndIdDocumento(idAnalise, idDocumento);
        analiseXDocumento.setStatusConferencia(StatusConferenciaDocumento.CONFERIDO_PELO_USUARIO);
        return repository.save(analiseXDocumento);
//        iniciarAnaliseSeNecessario(analiseXDocumento);
    }

    @Override
    @Transactional
    public AnaliseXDocumento rejeitarDocumento(Integer idDocumento, String comentario, Integer idAnalise) {
        AnaliseXDocumento analiseXDocumento = findByIdAnaliseAndIdDocumento(idAnalise, idDocumento);
        analiseXDocumento.setStatusConferencia(StatusConferenciaDocumento.REJEITADO);
        analiseXDocumento.setComentario(comentario);
        return repository.save(analiseXDocumento);
//        iniciarAnaliseSeNecessario(analiseXDocumento);
    }

    @Override
    public void documentoCorrigido(Integer idDocumento, Integer idAnalise, Integer idTipoDocumento, String ocorrencia) {
        AnaliseXDocumento analiseXDocumento = repository.findByAnalise_IdAndIdTipoDocumentoAndOcorrencia(idAnalise, idTipoDocumento, ocorrencia).orElseThrow(() -> new RuntimeException("Relação de Analise com tipo documento, não encontrado."));
        analiseXDocumento.setIdDocumento(idDocumento);
        analiseXDocumento.setStatusConferencia(StatusConferenciaDocumento.AINDA_NAO_CONFERIDO);
        repository.save(analiseXDocumento);
    }

    private AnaliseXDocumento findByIdDocumento(Integer idDocumento){
        return repository.findAnaliseXDocumentoByIdDocumento(idDocumento).orElseThrow(() -> new DocumentoNotFount(idDocumento));
    }

    public boolean primeiroDocumentoConferido(AnaliseXDocumento analiseXDocumento){
        Analise analise = analiseXDocumento.getAnalise();
        //se todos os documentos da analise estiverem aguardando conferência, exceto o que acabou de ser conferido
        return repository.findAllByAnalise_Id(analise.getId())
                .stream()
                .filter(item -> !item.getIdDocumento().equals(analiseXDocumento.getIdDocumento()))
                .allMatch(item -> item.getStatusConferencia().equals(StatusConferenciaDocumento.AINDA_NAO_CONFERIDO));
    }

    @Override
    public void saveAll(List<AnaliseXDocumento> analiseXDocumento) {
        repository.saveAll(analiseXDocumento);
    }

    @Override
    public boolean possuiDocumentosReprovados(Integer idAnalise) {
        return repository.findAllByAnalise_Id(idAnalise).stream().anyMatch(doc -> doc.getStatusConferencia().equals(StatusConferenciaDocumento.REJEITADO));
    }

    @Override
    public boolean possuiDocumentosAguardandoAssinatura(Integer idAnalise) {
        return repository.findAllByAnalise_Id(idAnalise).stream()
                .map(doc -> documentoDigitalService.getDocumento(doc.getIdDocumento().longValue()))
                .anyMatch(doc -> doc.getIdAssinado().equals(1));//documento não assinado
    }

    @Override
    public List<VoCelebracaoConsultaAnalisesDocumento> findAllVoDocumentosAnaliseById(Integer idAnalise) {
        return voCelebracaoConsultaAnalisesDocumentosRepository.findAllByIdAnalise(idAnalise);
    }
}
