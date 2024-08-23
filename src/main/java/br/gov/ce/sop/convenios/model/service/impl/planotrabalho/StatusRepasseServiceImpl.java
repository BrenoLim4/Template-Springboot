package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.StatusRepasse;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.StatusRepasseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.StatusRepasseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusRepasseServiceImpl implements StatusRepasseService {

    private final StatusRepasseRepository repository;

    @Override
    public List<StatusRepasse> getEntities(StatusRepasse filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<StatusRepasse> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<StatusRepasse> getAll() {
        return repository.findAll();
    }

    @Override
    public StatusRepasse getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}