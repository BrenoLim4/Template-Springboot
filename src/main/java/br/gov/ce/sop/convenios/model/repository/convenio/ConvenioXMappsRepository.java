package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConvenioXMappsRepository extends JpaRepository<ConvenioXMapp, Integer> {
    Boolean existsByMapp(Mapp mapp);
    Boolean existsByMapp_Id(Integer idMapp);
    List<ConvenioXMapp> findAllByConvenio_id(Integer idConvenio);
    Optional<ConvenioXMapp> findAllByMapp_Id(Integer idMapp);

    void deleteByMapp_Id(Integer idMapp);
}
