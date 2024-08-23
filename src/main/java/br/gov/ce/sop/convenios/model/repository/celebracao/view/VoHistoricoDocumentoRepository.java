package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoHistoricoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoHistoricoDocumentoRepository extends JpaRepository<VoHistoricoDocumento, Integer> {

    List<VoHistoricoDocumento> findAllByIdDocumento(Integer idDocumento);
}
