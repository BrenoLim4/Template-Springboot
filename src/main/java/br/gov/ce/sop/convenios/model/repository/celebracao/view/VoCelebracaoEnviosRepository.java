package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterEnviosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VoCelebracaoEnviosRepository extends JpaRepository<VoCelebracaoEnvios, Integer> {
    @Query("from VoCelebracaoEnvios v where " +
            "(:#{#filter.objeto} is null or lower(v.objeto) like %:#{#filter.objeto}%) " +
            "and (:#{#filter.idConvenente} is null or  v.idConvenente = :#{#filter.idConvenente}) " +
            "and (:#{#filter.nrProtocolo} is null or lower(v.nrProtocolo) like %:#{#filter.nrProtocolo}%)" +
            "and (coalesce(:#{#filter.dataInclusao}, null) is null or (v.dataInclusao >= :#{#filter.dataInclusao} and v.dataInclusao< :#{#fimDia}))")
    Page<VoCelebracaoEnvios> findAllByQuery(@Param("filter") FilterEnviosDTO filter, LocalDate fimDia , Pageable pageable);
}
