package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.TipoHistoricoAnalise;
import br.gov.ce.sop.convenios.model.repository.celebracao.TipoHistoricoAnaliseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.TipoHistoricoAnaliseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoHistoricoAnaliseServiceImpl implements TipoHistoricoAnaliseService {

    private final TipoHistoricoAnaliseRepository repository;

    @Override
    public List<TipoHistoricoAnalise> getEntities(TipoHistoricoAnalise filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<TipoHistoricoAnalise> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<TipoHistoricoAnalise> getAll() {
        return repository.findAll();
    }

    @Override
    public TipoHistoricoAnalise getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
