package br.gov.ce.sop.convenios.model.service.impl.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoListaDocumentosDigitaisAssinadores;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoListaDocumentosDigitaisAssinadoresRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoListaDocumentosDigitaisAssinadoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoListaDocumentosDigitaisAssinadoresServiceImpl implements VoListaDocumentosDigitaisAssinadoresService {
    private final VoListaDocumentosDigitaisAssinadoresRepository repository;
    @Override
    public List<VoListaDocumentosDigitaisAssinadores> getEntities(VoListaDocumentosDigitaisAssinadores filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoListaDocumentosDigitaisAssinadores> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoListaDocumentosDigitaisAssinadores> getAll() {
        return null;
    }

    @Override
    public VoListaDocumentosDigitaisAssinadores getEntityById(Integer integer) {
        return null;
    }


    @Override
    public List<VoListaDocumentosDigitaisAssinadores> findAllByIdDocumento(Long idDocumento) {
        return repository.findAllByIdDocumento(idDocumento);
    }
}
