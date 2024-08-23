package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.api.dto.filter.FilterProtocolosDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.VoProtocolos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface VoProtocolosRepository extends JpaRepository<VoProtocolos, Integer> {
    @Query(value = "from VoProtocolos v where " +
            "(:#{#filter.objeto} is null or lower(v.objeto) like %:#{#filter.objeto}%) " +
            "and (:#{#filter.convenente} is null or lower(v.convenente) like %:#{#filter.convenente}%) " +
            "and (coalesce(:#{#filter.dataInclusao}, null)  is null or (v.dataInclusao >= :#{#filter.dataInclusao} and v.dataInclusao< :#{#fimDia}))")
    Page<VoProtocolos> findAllByQuery(@Param("filter")FilterProtocolosDTO filter, LocalDateTime fimDia , Pageable pageable);

    @Query(value = "from VoProtocolos v where " +
            "(:#{#idEntidade} is null or v.id = :#{#idEntidade}) " +
            "and(:#{#idTipoDocumento} is null or v.idTipoDocumento = :#{#idTipoDocumento}) ")
    VoProtocolos findDocumentoProtocoloByQuery(@Param("idEntidade")Integer idEntidade, @Param("idTipoDocumento")Integer idTipoDocumento);
}
