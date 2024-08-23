package br.gov.ce.sop.convenios.model.service.impl.documentodigital;

import br.gov.ce.sop.convenios.api.dto.HashDocumentoDigitalDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.model.dto.DocumentosDigitais;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoHistoricoDocumento;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeitoService;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import java.io.*;

@Service
@RequiredArgsConstructor
public class DocumentoDigitalServiceProxyImpl implements DocumentoDigitalService {

    private final DocumentoDigitalServiceImpl documentoDigitalService;
    private final PrefeitoService prefeitoService;
    private final AnaliseService analiseService;

    @Override
    public DocumentosDigitais uploadDocumento(RParamsUploadDocumento params) {
        DocumentosDigitais documento = documentoDigitalService.uploadDocumento(params);
        if(params.versiona() && params.idAnalise() != null) {
            analiseService.documentoCorrigido(documento.getId().intValue(), params.idAnalise(), params.idTipoDocumento(), params.ocorrencia());
        }
        return documento;
    }

    @Override
    public byte[] downloadDocumento(Long id) {
        return documentoDigitalService.downloadDocumento(id);
    }

    @Override
    public byte[] downloadDocumentoZip(Integer id, String nrProtocolo) throws IOException {
        return documentoDigitalService.downloadDocumentoZip(id, nrProtocolo);
    }

    @Override
    public DocumentosDigitais getDocumento(Long id) {
        return documentoDigitalService.getDocumento(id);
    }

    @Override
    public List<DocumentosDigitais> getDocumentos(DocumentosDigitais documentosDigitais) {
        return documentoDigitalService.getDocumentos(documentosDigitais);
    }

    @Override
    public String deleteDocumento(Long id) {
        return documentoDigitalService.deleteDocumento(id);
    }

//    @Override
//    public String conferirDocumento(Long id) {
//        String response = documentoDigitalService.conferirDocumento(id);//vinculado ao contexto do controle documento digital
//        analiseXDocumentoService.conferirDocumento(id.intValue());//vinculado ao contexto do convênio
//        return response;
//    }

    @Override
    public String conferirDocumento(RParamsConferirDocumento params) {
        if (!Objects.isNull(params.origem()) && params.origem().equals(0))
            prefeitoService.conferirDocumento(params.id().intValue());
        else if(!Objects.isNull(params.origem()) && params.origem().equals(1))
            analiseService.conferirDocumento(params.id().intValue(), params.idAnalise());
        return documentoDigitalService.conferirDocumento(params.id());//vinculado ao contexto do controle documento digital
    }

    @Override
    public String rejeitarDocumento(RParamsRejeitarDocumento params) {

        if (!Objects.isNull(params.origem()) && params.origem().equals(0))
            prefeitoService.rejeitarDocumento(params.id().intValue(), params.comentario());
        else if(!Objects.isNull(params.origem()) && params.origem().equals(1))
            analiseService.rejeitarDocumento(params.id().intValue(), params.comentario(), params.idAnalise());//vinculado ao contexto do convênio
        return documentoDigitalService.rejeitarDocumento(params);//vinculado ao contexto do controle documento digital
    }

    @Override
    public void checarValidadePontoControle(Integer idPontoEntidade) {
        documentoDigitalService.checarValidadePontoControle(idPontoEntidade);
    }

    @Override
    public boolean conferenciaPendente(Integer idPontoEntidade) {
        return documentoDigitalService.conferenciaPendente(idPontoEntidade);
    }

    @Override
    public HashDocumentoDigitalDTO retornarHashOficio(String base64, Integer idTipoDocumento, String nomeSO, String matricula) throws Exception {
        return documentoDigitalService.retornarHashOficio(base64, idTipoDocumento, nomeSO, matricula);
    }

    @Override
    public byte[] anexarAssinaturaOficio(String assinatura, String base64, Integer idTipoDocumento, String nomeSO, String matricula) {
        try {
            return documentoDigitalService.anexarAssinaturaOficio(assinatura, base64, idTipoDocumento, nomeSO, matricula);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashDocumentoDigitalDTO retornarHashDocumentoDigital(RParamsHashDocumentoDigital params) {
        return documentoDigitalService.retornarHashDocumentoDigital(params);
    }

    @Override
    public String anexarAssinaturaDocumento(RParamsAnexarAssinatura params) {
        return documentoDigitalService.anexarAssinaturaDocumento(params);
    }

    @Override
    public String possuiAssinaturasValidas(String base64, Integer tipoAssinador) {
        return documentoDigitalService.possuiAssinaturasValidas(base64, tipoAssinador);
    }

    @Override
    public List<VoListaDocumentosDigitaisAssinadores> assinadoresDocumento(Long id) {
        return documentoDigitalService.assinadoresDocumento(id);
    }

    @Override
    public List<VoHistoricoDocumento> getHistoricoDocumento(Long id) {
        List<VoHistoricoDocumento> historicosDocumento = documentoDigitalService.getHistoricoDocumento(id);
        return historicosDocumento.stream().peek(h -> h.setUsuarioNome(h.getUsuarioNome().trim())).toList();
    }
}
