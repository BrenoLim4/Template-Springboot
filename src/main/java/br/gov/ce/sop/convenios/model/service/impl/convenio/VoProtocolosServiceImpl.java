package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.filter.FilterProtocolosDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.VoProtocolos;
import br.gov.ce.sop.convenios.model.repository.convenio.VoProtocolosRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoProtocolosService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VoProtocolosServiceImpl implements VoProtocolosService {
    private final VoProtocolosRepository repository;
    @Override
    public List<VoProtocolos> getEntities(VoProtocolos filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoProtocolos> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoProtocolos> getAll() {
        return repository.findAll();
    }

    @Override
    public VoProtocolos getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Page<VoProtocolos> findAllByQuery(FilterProtocolosDTO filter, Pageable pageable) {
        LocalDateTime fimDia = filter.getDataInclusao() == null
                ? null
                : filter.getDataInclusao().plusDays(1);
        return repository.findAllByQuery(filter, fimDia, pageable);
    }

    @Override
    public VoProtocolos findDocumentoProtocoloByQuery(Integer idEntidade, Integer idTipoDocumento) {
        return repository.findDocumentoProtocoloByQuery(idEntidade, idTipoDocumento);
    }
}
