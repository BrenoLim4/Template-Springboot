package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsultaAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoConsultaAnalisesRepository extends JpaRepository<VoCelebracaoConsultaAnalise, Integer> {

    List<VoCelebracaoConsultaAnalise> findAllByIdCelebracao(Integer idCelebracao);
}
