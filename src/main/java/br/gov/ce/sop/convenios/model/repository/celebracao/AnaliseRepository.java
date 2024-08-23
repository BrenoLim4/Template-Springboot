package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.enums.StatusAnalise;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnaliseRepository extends JpaRepository<Analise, Integer> {
    Optional<Analise> findByCelebracao_IdAndTipoAndAtualTrue(Integer idCelebracao, TipoAnalise tipoAnalise);

//    @Query(value = "from Analise a where a.celebracao.id = ?1 and not a.id = ?2 and a.atual")
//    Optional<Analise> findByCelebracao_IdAndIdNotAndAtualTrue(Integer idCelebracao, Integer idAnalise);
    Optional<Analise> findByCelebracao_IdAndIdNotAndTipoNotAndAtualTrue(Integer idCelebracao, Integer idAnalise, TipoAnalise tipo);

    List<Analise> findAllByCelebracao_IdAndStatusAnaliseAndAtualTrue(Integer idCelebracao, StatusAnalise status);
}

