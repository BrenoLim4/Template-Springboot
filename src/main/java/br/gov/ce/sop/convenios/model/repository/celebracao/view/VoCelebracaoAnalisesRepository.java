package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterAnaliseDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnalises;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VoCelebracaoAnalisesRepository extends JpaRepository<VoCelebracaoAnalises, Integer> {
    @Query("from VoCelebracaoAnalises v where " +
            "(:#{#filter.objeto} is null or lower(v.objeto) like %:#{#filter.objeto}%) " +
            "and (:#{#filter.idConvenente} is null or v.idConvenente = :#{#filter.idConvenente}) " +
            "and (:#{#filter.nrProtocolo} is null or lower(v.nrProtocolo) like %:#{#filter.nrProtocolo}%) " +
            "and (:#{#filter.idTipoAnalise} is null or v.idTipoAnalise = :#{#filter.idTipoAnalise})" +
            "and (:#{#filter.nrMapp} is null or v.mappsIds like %:#{#filter.nrMapp}%)" +
            "and (:#{#filter.matriculaEngenheiro} is null or v.matriculaEngenheiro = :#{#filter.matriculaEngenheiro})" +
            "and (coalesce(:#{#filter.dataInclusao}, null) is null or (v.dataInclusao >= :#{#filter.dataInclusao} and v.dataInclusao< :#{#fimDia}))")
    Page<VoCelebracaoAnalises> findAllByQuery(@Param("filter") FilterAnaliseDTO filter, LocalDate fimDia , Pageable pageable);

    VoCelebracaoAnalises findByIdCelebracaoAndIdTipoAnalise(Integer idCelebracao, Integer idTipoAnalise);
}
