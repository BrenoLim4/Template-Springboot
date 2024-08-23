package br.gov.ce.sop.convenios.model.service.impl.documentodigital;

import br.gov.ce.sop.convenios.model.entity.documentodigital.TipoDocumentoXTipoAssinador;
import br.gov.ce.sop.convenios.model.repository.documentodigital.TipoDocumentoXTipoAssinadorRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.TipoDocumentoXTipoAssinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoXTipoAssinadorServiceImpl implements TipoDocumentoXTipoAssinadorService {

    private final TipoDocumentoXTipoAssinadorRepository repository;

    @Override
    public List<TipoDocumentoXTipoAssinador> getEntities(TipoDocumentoXTipoAssinador filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<TipoDocumentoXTipoAssinador> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<TipoDocumentoXTipoAssinador> getAll() {
        return repository.findAll();
    }

    @Override
    public TipoDocumentoXTipoAssinador getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Integer> findAllByIdTipoDocumento(Integer idTipoDocumento) {
        return repository.findAllByIdTipoDocumento(idTipoDocumento).stream()
                .map(TipoDocumentoXTipoAssinador::getIdTipoAssinador)
                .toList();
    }

    @Override
    public Boolean findOneByIdDocumentoIdTipoAssinadorAndNotSigned(Integer idDocumento, Integer idTipoAssinador) {
        return null;
    }

}
