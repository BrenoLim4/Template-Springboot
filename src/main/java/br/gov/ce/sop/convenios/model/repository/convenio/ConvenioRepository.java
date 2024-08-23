package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ConvenioRepository extends JpaRepository<Convenio, Integer> {
    @Procedure(value = "public.fn_verificar_nr_protocolo" )
    String validarProtocolo(@Param("protocolo") String protocolo);
    boolean existsByNrSacc(Integer nrSacc);
}
