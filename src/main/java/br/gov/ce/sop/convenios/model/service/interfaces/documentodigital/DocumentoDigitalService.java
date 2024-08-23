package br.gov.ce.sop.convenios.model.service.interfaces.documentodigital;

import br.gov.ce.sop.convenios.api.dto.HashDocumentoDigitalDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.model.dto.DocumentosDigitais;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoHistoricoDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;

import java.io.IOException;
import java.util.List;

public interface DocumentoDigitalService {

    DocumentosDigitais uploadDocumento(RParamsUploadDocumento params);
    byte[] downloadDocumento(Long id) throws IOException;
    DocumentosDigitais getDocumento(Long id);
    List<DocumentosDigitais> getDocumentos(DocumentosDigitais documentosDigitais);
    String deleteDocumento(Long id);
    default String conferirDocumento(Long id){throw new RuntimeException("Não Implementado");}
    default String conferirDocumento(RParamsConferirDocumento params){throw new RuntimeException("Não Implementado");}
    byte[] downloadDocumentoZip(Integer id, String nrProtocolo) throws IOException;
    String rejeitarDocumento(RParamsRejeitarDocumento params);
    void checarValidadePontoControle(Integer idPontoEntidade);
    boolean conferenciaPendente(Integer idPontoEntidade);
    HashDocumentoDigitalDTO retornarHashOficio(String base64, Integer idTipoDocumento, String nomeSO, String matricula) throws Exception;
    byte[] anexarAssinaturaOficio(String assinatura, String base64, Integer idTipoDocumento, String nomeSO, String matricula) throws Exception;
    HashDocumentoDigitalDTO retornarHashDocumentoDigital(RParamsHashDocumentoDigital params);
    String anexarAssinaturaDocumento(RParamsAnexarAssinatura params);
    String possuiAssinaturasValidas(String base64, Integer tipoAssinador);
    List<VoListaDocumentosDigitaisAssinadores> assinadoresDocumento(Long id);
    List<VoHistoricoDocumento> getHistoricoDocumento(Long id);
}
