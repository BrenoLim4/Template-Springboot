package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.AssessoriaDTO;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Assessoria;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.PessoaJuridica;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeitura;
import br.gov.ce.sop.convenios.model.repository.convenio.prefeitura.AssessoriaRepository;
import br.gov.ce.sop.convenios.model.repository.convenio.prefeitura.PrefeituraRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrefeituraServiceImpl implements PrefeituraService {

    private final PrefeituraRepository repository;
    private final AssessoriaRepository assessoriaRepository;

    @Override
    public Prefeitura findPrefeituraLogada() {
        String cnpjLogado = TokenService.getTokenUsername();
//        String cnpjLogado = "00000000000000";
        Optional<Prefeitura> prefeitura = repository.findByPessoaJuridica_CnpjAndPessoaJuridica_IdTipoPessoaJuridica(cnpjLogado, PessoaJuridica.PREFEITURA);//tipo prefeitura
        return prefeitura.orElseThrow(() -> new RuntimeException("prefeitura não cadastrada no coorporativo."));
    }

    @Override
    public Integer getIdPessoa() {
        return findPrefeituraLogada().getIdPessoa();
    }

    @Override
    @Transactional
    public AssessoriaDTO cadastrarAssessoria(AssessoriaDTO assessoriaDTO) {
        //Busca a assessoria por cnpj, caso não exista cria uma nova
        Assessoria assessoria = assessoriaRepository.findByCnpj(assessoriaDTO.cnpj()).orElse(new Assessoria());
        BeanUtils.copyProperties(assessoriaDTO, assessoria);
        assessoria = assessoriaRepository.save(assessoria);
        Prefeitura prefeituraLogada = findPrefeituraLogada();
        if (!assessoria.equals(prefeituraLogada.getAssessoria())){
            prefeituraLogada.setAssessoria(assessoria);
            repository.save(prefeituraLogada);
        }
        return assessoriaDTO;
    }

    @Override
    public AssessoriaDTO getAssessoriaLogada() {
        Assessoria assessoria = findPrefeituraLogada().getAssessoria();

        return Optional.ofNullable(assessoria)
                .map(ass -> AssessoriaDTO.builder()
                        .cnpj(assessoria.getCnpj())
                        .email(assessoria.getPessoa().getEmail())
                        .razaoSocial(assessoria.getPessoa().getRazaoSocial())
                        .nomeFantasia(assessoria.getNomeFantasia())
                        .build())
                .orElse(null);
    }

    @Override
    public String desassociarAssessoria() {
        Prefeitura prefeituraLogada = findPrefeituraLogada();
        Assessoria assessoria = prefeituraLogada.getAssessoria();
        if(Objects.isNull(assessoria)){
            throw new WarningException("Nenhuma assessoria cadastrada.");
        }
        prefeituraLogada.setAssessoria(null);
        repository.save(prefeituraLogada);
        return "Assessoria desassociada com sucesso!";
    }

}
