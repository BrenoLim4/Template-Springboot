package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.api.dto.CelebracaoEnvioDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvioDocumentos;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoEnvioDocumentosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoEnvioDocumentosService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoCelebracaoEnvioDocumentosServiceImpl implements VoCelebracaoEnvioDocumentosService {

    private final VoCelebracaoEnvioDocumentosRepository repository;

    @Override
    public List<VoCelebracaoEnvioDocumentos> getEntities(VoCelebracaoEnvioDocumentos filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoEnvioDocumentos> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoEnvioDocumentos> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCelebracaoEnvioDocumentos getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<VoCelebracaoEnvioDocumentos> buscarDocumentosEnvio(CelebracaoEnvioDocumentosDTO params, Pageable pageable) {
        return  repository.findAllByIdCelebracao(params.getIdCelebracao(), pageable);
    }
}
