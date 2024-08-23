package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterConsultaCelebracaoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoCelebracaoConsultaRepository extends JpaRepository<VoCelebracaoConsulta, Integer> {

    @Query(" from VoCelebracaoConsulta v where " +
            "(:#{#filter.convenente()} is null or v.convenente ilike %:#{#filter.convenente()}%) " +
            "and (:#{#filter.cnpjConvenente()} is null or v.cnpjConvenente like %:#{#filter.cnpjConvenente()}%) " +
            "and (:#{#filter.nrProtocolo()} is null or v.nrProtocolo like %:#{#filter.nrProtocolo()}%) " +
            "and (:#{#filter.mapp()} is null or v.mapps like %:#{#filter.mapp()}%) " +
            "and (:#{#filter.objetoConvenio()} is null or v.objetoConvenio ilike %:#{#filter.objetoConvenio()}%) " +
            "and (:#{#filter.status()} is null or v.idStatus in (:#{#filter.status()})) " +
            "and (:#{#filter.tiposConvenio()} is null or v.idTipoConvenio in (:#{#filter.tiposConvenio()})) " +
            "and (:#{#filter.matriculaEngenheiro()} is null or v.matriculaEngenheiro = :#{#filter.matriculaEngenheiro()}) " +
            "and (:#{#filter.apenasAguardandoAtribuicaoEngenheiro()} is null or v.aguardandoAtribuicaoEngenheiro = :#{#filter.apenasAguardandoAtribuicaoEngenheiro()})")
    Page<VoCelebracaoConsulta> findAllByQuery(@Param("filter") FilterConsultaCelebracaoDTO filter, Pageable pageable);

}
