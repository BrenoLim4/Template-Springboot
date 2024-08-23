package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.model.entity.planotrabalho.ProcessoRepasse;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.ProcessoRepasseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.ProcessoRepasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoRepasseServiceImpl implements ProcessoRepasseService {

    private final ProcessoRepasseRepository processoRepasseRepository;

    @Override
    public List<ProcessoRepasse> getEntities(ProcessoRepasse filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();
        Example<ProcessoRepasse> example = Example.of(filtro, matcher);
        return processoRepasseRepository.findAll(example);
    }

    @Override
    public List<ProcessoRepasse> getAll() {
        return processoRepasseRepository.findAll();
    }

    @Override
    public ProcessoRepasse getEntityById(Integer id) {
        return processoRepasseRepository.findById(id).orElseThrow(() -> new RuntimeException("Processo não encontrado!"));
    }
}
