package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsultaAnalisesDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoConsultaAnalisesDocumentosRepository extends JpaRepository<VoCelebracaoConsultaAnalisesDocumento, Integer> {
    List<VoCelebracaoConsultaAnalisesDocumento> findAllByIdAnalise(Integer idAnalise);
}
