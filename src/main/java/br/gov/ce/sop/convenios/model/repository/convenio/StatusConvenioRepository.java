package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.StatusConvenio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusConvenioRepository extends JpaRepository<StatusConvenio, Integer> {
}
