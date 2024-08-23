package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.AnaliseXDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnaliseXDocumentoRepository extends JpaRepository<AnaliseXDocumento, Integer> {

    Optional<AnaliseXDocumento> findByAnalise_idAndIdDocumento(Integer idAnalise, Integer idDocumento);

    Optional<AnaliseXDocumento> findAnaliseXDocumentoByIdDocumento(Integer idDocumento);

    List<AnaliseXDocumento> findAllByAnalise_Id(Integer idAnalise);

    Optional<AnaliseXDocumento> findByAnalise_IdAndIdTipoDocumentoAndOcorrencia(Integer idAnalise, Integer idTipoDocumento, String ocorrencia);
}
