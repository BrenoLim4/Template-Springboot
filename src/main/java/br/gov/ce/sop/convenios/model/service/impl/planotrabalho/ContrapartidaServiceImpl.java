package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.Contrapartida;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.ContrapartidaRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.ContrapartidaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContrapartidaServiceImpl implements ContrapartidaService {

    private final ContrapartidaRepository repository;

    @Override
    public List<Contrapartida> getEntities(Contrapartida filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Contrapartida> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<Contrapartida> getAll() {
        return repository.findAll();
    }

    @Override
    public Contrapartida getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
