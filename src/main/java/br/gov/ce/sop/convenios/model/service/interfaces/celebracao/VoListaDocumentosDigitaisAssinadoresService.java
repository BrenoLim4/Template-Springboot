package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface VoListaDocumentosDigitaisAssinadoresService extends BasicEntityService<VoListaDocumentosDigitaisAssinadores, Integer, VoListaDocumentosDigitaisAssinadores> {
    List<VoListaDocumentosDigitaisAssinadores> findAllByIdDocumento(Long idDocumento);
}
