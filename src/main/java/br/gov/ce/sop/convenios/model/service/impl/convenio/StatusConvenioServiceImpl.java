package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.StatusConvenio;
import br.gov.ce.sop.convenios.model.repository.convenio.StatusConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.StatusConvenioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusConvenioServiceImpl implements StatusConvenioService {

    private final StatusConvenioRepository repository;

    @Override
    public List<StatusConvenio> getEntities(StatusConvenio filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<StatusConvenio> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<StatusConvenio> getAll() {
        return repository.findAll();
    }

    @Override
    public StatusConvenio getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
