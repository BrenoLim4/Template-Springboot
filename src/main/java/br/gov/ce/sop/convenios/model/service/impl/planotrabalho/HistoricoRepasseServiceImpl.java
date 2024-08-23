package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.HistoricoRepasse;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.HistoricoRepasseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.HistoricoRepasseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoRepasseServiceImpl implements HistoricoRepasseService {

    private final HistoricoRepasseRepository repository;

    @Override
    public List<HistoricoRepasse> getEntities(HistoricoRepasse filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<HistoricoRepasse> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<HistoricoRepasse> getAll() {
        return repository.findAll();
    }

    @Override
    public HistoricoRepasse getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
