package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.TipoConvenio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoConvenioRepository extends JpaRepository<TipoConvenio, Integer> {
}