package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.AnaliseXDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsultaAnalisesDocumento;

import java.util.List;

public interface AnaliseXDocumentoService {

    AnaliseXDocumento findByIdAnaliseAndIdDocumento(Integer idAnalise, Integer idDocumento);
    List<AnaliseXDocumento> findAllByAnalise(Analise analise);
    AnaliseXDocumento conferirDocumento(Integer idDocumento, Integer idAnalise);
    AnaliseXDocumento rejeitarDocumento(Integer idDocumento, String comentario, Integer idAnalise);
    void documentoCorrigido(Integer idDocumento, Integer idAnalise, Integer idTipoDocumento, String ocorrencia);
    boolean primeiroDocumentoConferido(AnaliseXDocumento analiseXDocumento);
    void saveAll(List<AnaliseXDocumento> analiseXDocumento);
    boolean possuiDocumentosReprovados(Integer idAnalise);
    boolean possuiDocumentosAguardandoAssinatura(Integer idAnalise);
    List<VoCelebracaoConsultaAnalisesDocumento> findAllVoDocumentosAnaliseById(Integer idAnalise);
}
