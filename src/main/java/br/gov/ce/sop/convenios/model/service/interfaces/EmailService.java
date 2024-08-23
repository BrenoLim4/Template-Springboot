package br.gov.ce.sop.convenios.model.service.interfaces;

import br.gov.ce.sop.convenios.model.dto.email.SendMailDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;

public interface EmailService {

    void sendMail(SendMailDTO params);
    void sendMailNovoConvenioSolicitado(Integer idCelebracao, String oficioCelebracao);
    void sendMailCelebracaoProtocolada(Integer idCelebracao, String nrProtocolo);
    void sendMailDocumentosEnviados(Integer idCelebracao);
    void sendMailCelebracaoConferida(Analise analise);
    void sendMailDocumentosCorrigidos(Integer idCelebracao);
    void sendMailParecerCadastrado(Integer idCelebracao);
    void sendMailDocumentoRecusado();
}
