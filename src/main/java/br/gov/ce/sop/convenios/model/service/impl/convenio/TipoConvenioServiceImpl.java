package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.TipoConvenio;
import br.gov.ce.sop.convenios.model.repository.convenio.TipoConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.TipoConvenioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoConvenioServiceImpl implements TipoConvenioService {

    private final TipoConvenioRepository repository;

    @Override
    public List<TipoConvenio> getEntities(TipoConvenio filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<TipoConvenio> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<TipoConvenio> getAll() {
        return repository.findAll();
    }

    @Override
    public TipoConvenio getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
