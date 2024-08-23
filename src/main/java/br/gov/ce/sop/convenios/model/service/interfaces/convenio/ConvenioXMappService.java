package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.Optional;

public interface ConvenioXMappService extends BasicEntityService<ConvenioXMapp, Integer, ConvenioXMapp> {

    Boolean existsByMapp(Mapp mapp);

    Boolean existsByMapp(Integer id);
    ConvenioXMapp salvarConvXMapp(ConvenioXMapp convenioxMapp);

    void deleteByIdMapp(Integer idMapp);

    Optional<ConvenioXMapp> findConvenioXMappByidMapp(Integer idMapp);

}
