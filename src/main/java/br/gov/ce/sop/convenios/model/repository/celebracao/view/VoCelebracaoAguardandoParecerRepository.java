package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAguardandoParecerDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoCelebracaoAguardandoParecerRepository extends JpaRepository<VoCelebracaoAguardandoParecer, Integer> {

    @Query("select v from VoCelebracaoAguardandoParecer v where " +
            "(:#{#filter.convenente} is null or v.convenente like %:#{#filter.convenente}%) " +
            "and (:#{#filter.cnpjConvenente} is null or v.cnpjConvenente = :#{#filter.cnpjConvenente}) " +
            "and (:#{#filter.mapps} is null or v.mapps like %:#{#filter.mapps}%) " +
            "and (:#{#filter.objeto} is null or v.objeto ilike %:#{#filter.objeto}%) " +
            "and (:#{#filter.nrProtocolo} is null or v.nrProtocolo like %:#{#filter.nrProtocolo}%)")
    Page<VoCelebracaoAguardandoParecer> findAllByQuery(@Param("filter") FilterCelebracaoAguardandoParecerDTO filter, Pageable pageable);

//    @Query(" from VoCelebracaoAguardandoParecer v ")
//    Page<VoCelebracaoAguardandoParecer> findAllByQuery(@Param("filter") FilterCelebracaoAguardandoParecerDTO filter, Pageable pageable);
}
