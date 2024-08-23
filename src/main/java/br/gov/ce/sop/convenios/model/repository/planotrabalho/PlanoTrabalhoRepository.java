package br.gov.ce.sop.convenios.model.repository.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.PlanoTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoTrabalhoRepository extends JpaRepository<PlanoTrabalho, Integer> {

    boolean existsByConvenio_Id(Integer idConvenio);

    Optional<PlanoTrabalho> findByConvenio_Id(Integer idConvenio);
}
