package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.VoCadastrosConvenio;
import br.gov.ce.sop.convenios.model.repository.convenio.VoCadastrosConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.VoCadastrosConvenioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class VoCadastrosConvenioServiceImpl implements VoCadastrosConvenioService {

    private final VoCadastrosConvenioRepository repository;
    private final PrefeituraService prefeituraService;

    @Override
    public List<VoCadastrosConvenio> getEntities(VoCadastrosConvenio filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoCadastrosConvenio> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<VoCadastrosConvenio> getAll() {
        return repository.findAll();
    }

    @Override
    public VoCadastrosConvenio getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<VoCadastrosConvenio> buscarUltimosCadastros(String idConvenente) {
        return repository.findTop5ByIdConvenenteOrderByDataCadastroDesc(prefeituraService.getIdPessoa());
    }
}
