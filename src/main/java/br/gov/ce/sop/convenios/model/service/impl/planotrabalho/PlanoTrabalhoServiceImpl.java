package br.gov.ce.sop.convenios.model.service.impl.planotrabalho;

import br.gov.ce.sop.convenios.api.dto.parametros.PlanoTrabalhoDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.planotrabalho.PlanoTrabalho;
import br.gov.ce.sop.convenios.model.repository.planotrabalho.PlanoTrabalhoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.PlanoTrabalhoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PlanoTrabalhoServiceImpl implements PlanoTrabalhoService {

    private final PlanoTrabalhoRepository repository;

    @Override
    public List<PlanoTrabalho> getEntities(PlanoTrabalho filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<PlanoTrabalho> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<PlanoTrabalho> getAll() {
        return repository.findAll();
    }

    @Override
    public PlanoTrabalho getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public String cadastrar(PlanoTrabalhoDTO planoTrabalhoDTO) {
        PlanoTrabalho planoTrabalho = repository.findByConvenio_Id(planoTrabalhoDTO.idConvenio())
                .orElse(PlanoTrabalho
                        .builder()
                        .convenio(new Convenio(planoTrabalhoDTO.idConvenio()))
                        .dataEmissao(LocalDateTime.now())
                        .build());
        BeanUtils.copyProperties(planoTrabalhoDTO, planoTrabalho);
        repository.save(planoTrabalho);
        return "Plano trabalho cadastro com sucesso.";
    }

    @Override
    public boolean existsByIdConvenio(Integer idConvenio) {
        return repository.existsByConvenio_Id(idConvenio);
    }

    @Override
    public PlanoTrabalhoDTO findByIdConvenio(Integer idConvenio) {
        return PlanoTrabalhoDTO.convertToDTO(repository.findByConvenio_Id(idConvenio).orElseThrow());
    }

    @Override
    @Transactional
    public void atualizar(RCadastrarPublicacao params) {
        repository.findByConvenio_Id(params.idConvenio())
                .ifPresent(pt -> {
                    BeanUtils.copyProperties(params, pt);
                    pt.setDescricao(params.objeto());
                    repository.save(pt);
                });
    }
}