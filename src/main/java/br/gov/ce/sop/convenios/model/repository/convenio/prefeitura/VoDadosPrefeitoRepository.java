package br.gov.ce.sop.convenios.model.repository.convenio.prefeitura;

import br.gov.ce.sop.convenios.api.dto.filter.FilterDadosPrefeitoDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDadosPrefeito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoDadosPrefeitoRepository extends JpaRepository<VoDadosPrefeito, Integer> {

    @Query(" from VoDadosPrefeito v where " +
            "(:#{#filter.nome()} is null or v.nome ilike %:#{#filter.nome()}%) " +
            "and (:#{#filter.cnpj()} is null or v.cnpj like %:#{#filter.cnpj()}%) " +
            "and (:#{#filter.razaoSocial()} is null or v.razaoSocial ilike %:#{#filter.razaoSocial()}%) " +
            "and (:#{#filter.ativo()} is null or v.ativo = :#{#filter.ativo()})")
    Page<VoDadosPrefeito> findAllByQuery(@Param("filter") FilterDadosPrefeitoDTO filter, Pageable pageable);
}
