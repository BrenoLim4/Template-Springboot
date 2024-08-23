package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.Repasse;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.RepasseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.RepasseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RepasseServiceImpl implements RepasseService {

    private final RepasseRepository repository;

    @Override
    public List<Repasse> getEntities(Repasse filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Repasse> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<Repasse> getAll() {
        return repository.findAll();
    }

    @Override
    public Repasse getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}