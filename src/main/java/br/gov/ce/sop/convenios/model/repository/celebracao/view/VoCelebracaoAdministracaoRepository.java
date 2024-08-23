package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAdministrativoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAdministracao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoCelebracaoAdministracaoRepository extends JpaRepository<VoCelebracaoAdministracao, Integer> {

    @Query(" from VoCelebracaoAdministracao v where " +
            "(:#{#filter.convenente} is null or v.convenente ilike %:#{#filter.convenente}%) " +
            "and (:#{#filter.cnpjConvenente} is null or v.cnpjConvenente like %:#{#filter.cnpjConvenente}%) " +
            "and (:#{#filter.nrProtocolo} is null or v.nrProtocolo like %:#{#filter.nrProtocolo}%) " +
            "and (:#{#filter.mapps} is null or v.mapps like %:#{#filter.mapps}%) " +
            "and (:#{#filter.objeto} is null or v.objeto ilike %:#{#filter.objeto}%) " +
            "and (:#{#filter.status} is null or v.idStatus in (:#{#filter.status}))")
    Page<VoCelebracaoAdministracao> findAllByQuery(@Param("filter") FilterCelebracaoAdministrativoDTO filter, Pageable pageable);
}
