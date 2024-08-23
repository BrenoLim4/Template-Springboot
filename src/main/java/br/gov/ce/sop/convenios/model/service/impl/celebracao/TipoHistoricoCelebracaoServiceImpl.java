package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.TipoHistoricoCelebracao;
import br.gov.ce.sop.convenios.model.repository.celebracao.TipoHistoricoCelebracaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.TipoHistoricoCelebracaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoHistoricoCelebracaoServiceImpl implements TipoHistoricoCelebracaoService {

    private final TipoHistoricoCelebracaoRepository repository;

    @Override
    public List<TipoHistoricoCelebracao> getEntities(TipoHistoricoCelebracao filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<TipoHistoricoCelebracao> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<TipoHistoricoCelebracao> getAll() {
        return repository.findAll();
    }

    @Override
    public TipoHistoricoCelebracao getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
