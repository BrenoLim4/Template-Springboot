package br.gov.ce.sop.convenios.model.repository.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.Repasse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepasseRepository extends JpaRepository<Repasse, Integer> {
}
