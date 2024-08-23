package br.gov.ce.sop.convenios.model.repository.mapp;

import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MappRepository extends JpaRepository<Mapp, Integer>{
    @Query( value = "select m.id, m.codigo_mapp, m.mapp, m.status from webservice_seplag.mapp m " +
            "where m.codigo_mapp like '%' || :codigoMapp || '%' " +
            "order by cast(m.codigo_mapp as INTEGER) " +
            "limit 15", nativeQuery = true )
    List<Mapp> findAllByQuery(@Param("codigoMapp") String codigoMapp);
}

