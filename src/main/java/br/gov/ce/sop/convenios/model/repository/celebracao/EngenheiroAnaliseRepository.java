package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.EngenheiroAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngenheiroAnaliseRepository extends JpaRepository<EngenheiroAnalise, String> {

    List<EngenheiroAnalise> findAllByAtivoTrue();
}
