package br.gov.ce.sop.convenios.model.repository.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.HistoricoRepasse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepasseRepository extends JpaRepository<HistoricoRepasse, Integer> {
}
