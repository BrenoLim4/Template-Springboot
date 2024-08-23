package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterAnaliseDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnalises;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoAnalisesRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoAnalisesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VoCelebracaoAnalisesServiceImpl implements VoCelebracaoAnalisesService {

    private final VoCelebracaoAnalisesRepository repository;

    @Override
    public List<VoCelebracaoAnalises> getEntities(VoCelebracaoAnalises filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoAnalises> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoAnalises> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoAnalises getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<VoCelebracaoAnalises> findAllByQuery(FilterAnaliseDTO filter, Pageable pageable) {
        LocalDate fimDia = filter.getDataInclusao() == null
                ? null
                : filter.getDataInclusao().plusDays(1);
        return repository.findAllByQuery(filter, fimDia, pageable);
    }
}
