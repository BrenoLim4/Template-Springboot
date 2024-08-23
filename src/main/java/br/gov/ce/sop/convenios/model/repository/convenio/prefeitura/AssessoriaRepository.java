package br.gov.ce.sop.convenios.model.repository.convenio.prefeitura;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Assessoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessoriaRepository extends JpaRepository<Assessoria, Integer> {

    Optional<Assessoria> findByCnpj(String cnpj);
}
