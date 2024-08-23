package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.StatusAnalise;
import br.gov.ce.sop.convenios.model.repository.celebracao.StatusAnaliseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.StatusAnaliseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusAnaliseServiceImpl implements StatusAnaliseService {

    private final StatusAnaliseRepository repository;

    @Override
    public List<StatusAnalise> getEntities(StatusAnalise filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<StatusAnalise> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<StatusAnalise> getAll() {
        return repository.findAll();
    }

    @Override
    public StatusAnalise getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
