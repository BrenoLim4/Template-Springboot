package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.StatusCelebracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusCelebracaoRepository extends JpaRepository<StatusCelebracao, Integer> {
}
