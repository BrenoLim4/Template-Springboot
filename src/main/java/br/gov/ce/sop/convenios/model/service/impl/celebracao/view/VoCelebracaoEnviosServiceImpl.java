package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.filter.FilterEnviosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvios;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoEnviosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoEnviosService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
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
public class VoCelebracaoEnviosServiceImpl implements VoCelebracaoEnviosService {

    private final VoCelebracaoEnviosRepository repository;
    private final PrefeituraService prefeituraService;

    @Override
    public List<VoCelebracaoEnvios> getEntities(VoCelebracaoEnvios filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoEnvios> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoEnvios> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoEnvios getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<VoCelebracaoEnvios> findAllByQuery(FilterEnviosDTO filter, Pageable pageable) {
        filter.setIdConvenente(prefeituraService.getIdPessoa());
        LocalDate fimDia = filter.getDataInclusao() == null
                ? null
                : filter.getDataInclusao().plusDays(1);
        return repository.findAllByQuery(filter, fimDia, pageable);
    }
}
