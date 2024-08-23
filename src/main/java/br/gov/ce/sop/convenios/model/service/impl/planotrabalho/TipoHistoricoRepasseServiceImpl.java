package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.TipoHistoricoRepasse;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.TipoHistoricoRepasseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.TipoHistoricoRepasseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoHistoricoRepasseServiceImpl implements TipoHistoricoRepasseService {

    private final TipoHistoricoRepasseRepository repository;

    @Override
    public List<TipoHistoricoRepasse> getEntities(TipoHistoricoRepasse filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<TipoHistoricoRepasse> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<TipoHistoricoRepasse> getAll() {
        return repository.findAll();
    }

    @Override
    public TipoHistoricoRepasse getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
