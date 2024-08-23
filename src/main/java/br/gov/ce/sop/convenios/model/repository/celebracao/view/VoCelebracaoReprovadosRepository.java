package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterReprovadoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VoCelebracaoReprovadosRepository extends JpaRepository<VoCelebracaoReprovados, Integer> {
    @Query("from VoCelebracaoReprovados v where " +
            "(:#{#filter.objeto} is null or lower(v.objeto) like %:#{#filter.objeto}%) " +
            "and (:#{#filter.idConvenente} is null or v.idConvenente = :#{#filter.idConvenente}) " +
            "and (:#{#filter.nrProtocolo} is null or lower(v.nrProtocolo) like %:#{#filter.nrProtocolo}%) " +
            "and (coalesce(:#{#filter.dataInclusao}, null) is null or (v.dataInclusao >= :#{#filter.dataInclusao} and v.dataInclusao< :#{#fimDia}))")
    Page<VoCelebracaoReprovados> findAllByQuery(@Param("filter") FilterReprovadoDTO filter, LocalDate fimDia , Pageable pageable);
}
