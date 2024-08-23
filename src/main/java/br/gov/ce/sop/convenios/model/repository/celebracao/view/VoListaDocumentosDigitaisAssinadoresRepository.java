package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoListaDocumentosDigitaisAssinadoresRepository extends JpaRepository<VoListaDocumentosDigitaisAssinadores, Integer> {
    List<VoListaDocumentosDigitaisAssinadores> findAllByIdDocumento(Long idDocumento);
}
