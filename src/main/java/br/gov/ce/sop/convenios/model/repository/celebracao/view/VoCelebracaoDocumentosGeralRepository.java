package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDocumentosGeral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoDocumentosGeralRepository extends JpaRepository<VoCelebracaoDocumentosGeral, Integer> {
    List<VoCelebracaoDocumentosGeral> findAllByIdCelebracao(Integer idCelebracao);
}
