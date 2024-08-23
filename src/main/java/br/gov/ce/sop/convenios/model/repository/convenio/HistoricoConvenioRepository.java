package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.HistoricoConvenio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoConvenioRepository extends JpaRepository<HistoricoConvenio, Integer> {
}
