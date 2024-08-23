package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.StatusCelebracao;
import br.gov.ce.sop.convenios.model.repository.celebracao.StatusCelebracaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.StatusCelebracaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusCelebracaoServiceImpl implements StatusCelebracaoService {

    private final StatusCelebracaoRepository repository;

    @Override
    public List<StatusCelebracao> getEntities(StatusCelebracao filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<StatusCelebracao> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<StatusCelebracao> getAll() {
        return repository.findAll();
    }

    @Override
    public StatusCelebracao getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
