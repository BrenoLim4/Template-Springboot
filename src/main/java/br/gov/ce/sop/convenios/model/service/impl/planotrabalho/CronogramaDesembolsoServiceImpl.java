package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.CronogramaDesembolso;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.CronogramaDesembolsoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.CronogramaDesembolsoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CronogramaDesembolsoServiceImpl implements CronogramaDesembolsoService {

    private final CronogramaDesembolsoRepository repository;

    @Override
    public List<CronogramaDesembolso> getEntities(CronogramaDesembolso filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<CronogramaDesembolso> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<CronogramaDesembolso> getAll() {
        return repository.findAll();
    }

    @Override
    public CronogramaDesembolso getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
