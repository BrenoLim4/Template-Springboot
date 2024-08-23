package br.gov.ce.sop.convenios.model.service.impl.convenio;


import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.repository.convenio.ConvenioXMappsRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ConvenioXMappService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConvenioXMappServiceImpl implements ConvenioXMappService {

    private final ConvenioXMappsRepository repository;

    @Override
    public List<ConvenioXMapp> getEntities(ConvenioXMapp filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<ConvenioXMapp> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<ConvenioXMapp> getAll() {
        return repository.findAll();
    }

    @Override
    public ConvenioXMapp getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Boolean existsByMapp(Mapp mapp) {
        return repository.existsByMapp(mapp);
    }

    @Override
    public Boolean existsByMapp(Integer id) {
        return repository.existsByMapp_Id(id);
    }

    @Override
    public ConvenioXMapp salvarConvXMapp(ConvenioXMapp convenioxMapp) {
        return repository.save(convenioxMapp);
    }

    @Override
    public void deleteByIdMapp(Integer idMapp) {
        repository.deleteByMapp_Id(idMapp);
    }

    @Override
    public Optional<ConvenioXMapp> findConvenioXMappByidMapp(Integer idMapp) {
        return repository.findAllByMapp_Id(idMapp);
    }

}
