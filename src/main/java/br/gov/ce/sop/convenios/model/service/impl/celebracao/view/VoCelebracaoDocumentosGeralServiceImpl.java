package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDocumentosGeral;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoCelebracaoDocumentosGeralRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoCelebracaoDocumentosGeralServiceImpl implements BasicEntityService<VoCelebracaoDocumentosGeral, Integer, VoCelebracaoDocumentosGeral> {
    private final VoCelebracaoDocumentosGeralRepository repository;
    @Override
    public List<VoCelebracaoDocumentosGeral> getEntities(VoCelebracaoDocumentosGeral filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCelebracaoDocumentosGeral> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCelebracaoDocumentosGeral> getAll() {
        return null;
    }

    @Override
    public VoCelebracaoDocumentosGeral getEntityById(Integer integer) {
        return null;
    }

}
