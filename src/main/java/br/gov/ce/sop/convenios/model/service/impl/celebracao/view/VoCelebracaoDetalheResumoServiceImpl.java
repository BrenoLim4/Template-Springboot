package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDetalheResumo;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoDetalheResumoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoDetalheResumoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoCelebracaoDetalheResumoServiceImpl implements VoCelebracaoDetalheResumoService {

    private final VoCelebracaoDetalheResumoRepository repository;

    @Override
    public List<VoCelebracaoDetalheResumo> getEntities(VoCelebracaoDetalheResumo filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoDetalheResumo> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoDetalheResumo> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoDetalheResumo getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
