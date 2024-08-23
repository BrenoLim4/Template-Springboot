package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterReprovadoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovados;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoReprovadosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoReprovadosService;
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
public class VoCelebracaoReprovadosServiceImpl implements VoCelebracaoReprovadosService {

    private final VoCelebracaoReprovadosRepository repository;

    @Override
    public List<VoCelebracaoReprovados> getEntities(VoCelebracaoReprovados filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoReprovados> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoReprovados> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoReprovados getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<VoCelebracaoReprovados> findAllByQuery(FilterReprovadoDTO filter, Pageable pageable) {
        LocalDate fimDia = filter.getDataInclusao() == null
                ? null
                : filter.getDataInclusao().plusDays(1);
        return repository.findAllByQuery(filter, fimDia, pageable);
    }
}
