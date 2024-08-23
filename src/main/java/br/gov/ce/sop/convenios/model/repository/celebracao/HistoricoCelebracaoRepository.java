package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoCelebracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoCelebracaoRepository extends JpaRepository<HistoricoCelebracao, Integer> {

    List<HistoricoCelebracao> findByIdCelebracao(Integer idCelebracao);
}
