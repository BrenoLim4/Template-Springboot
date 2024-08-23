package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CelebracaoRepository extends JpaRepository<Celebracao, Integer> {

    boolean existsByNrProtocolo(String nrProtocolo);
    Optional<Celebracao> findByConvenio_Id(Integer convenioId);
}
