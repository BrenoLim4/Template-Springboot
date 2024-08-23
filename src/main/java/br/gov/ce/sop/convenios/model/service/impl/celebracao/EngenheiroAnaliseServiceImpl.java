package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.api.dto.EngenheiroAnaliseDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RAtribuirEngenheiroAnalise;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.kafka.dto.EngenheiroCadastradoMessage;
import br.gov.ce.sop.convenios.kafka.producer.KafkaProducer;
import br.gov.ce.sop.convenios.kafka.producer.ProducerRecord;
import br.gov.ce.sop.convenios.model.entity.celebracao.EngenheiroAnalise;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoEngenheirosAnalise;
import br.gov.ce.sop.convenios.model.repository.celebracao.EngenheiroAnaliseRepository;
import br.gov.ce.sop.convenios.model.repository.celebracao.view.VoEngenheirosAnaliseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.AnaliseService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.EngenheiroAnaliseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EngenheiroAnaliseServiceImpl implements EngenheiroAnaliseService {

    private final EngenheiroAnaliseRepository repository;
    private final AnaliseService analiseService;
    private final VoEngenheirosAnaliseRepository engenheirosAnaliseRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<EngenheiroAnalise> findAllEngenheirosAnaliseAtivos() {
        return repository.findAllByAtivoTrue();
    }

    @Override
    public String atribuirAnalise(RAtribuirEngenheiroAnalise params) {
        Optional<EngenheiroAnalise> engenheiro = repository.findById(params.matricula());
        StringBuilder msgBuilder = new StringBuilder();
        engenheiro.ifPresent(eng -> {
            String msg = analiseService.atribuirEngenheiro(params.matricula(), params.idCelebracao());
            msgBuilder.append("Engenheiro ").append(eng.getMatricula()).append(" - ").append(eng.getNome()).append(" ").append(msg);
        });
        return msgBuilder.toString();
    }

    @Override
    @Transactional
    public void cadastrar(EngenheiroAnaliseDTO params) {
        /*
        * Realizar cadastro do engenheiro
        * produzir uma mensagem no kafka
        * consumir na api de controle de acesso e criar o usuário caso necessário
        * */
        repository.findById(params.matricula())
                .ifPresent(engenheiro -> {
                    throw new WarningException(String.format("A matrícula %s já está atribuída ao engenheiro %s", engenheiro.getMatricula(), engenheiro.getNome()));
                });
        save(params);
        kafkaProducer.sendMessage(ProducerRecord.builder().topico("engenheiro-cadastrado").message(() -> new EngenheiroCadastradoMessage(params))::build);
    }

    @Override
    public void save(EngenheiroAnaliseDTO engenheiroAnaliseDTO){
        EngenheiroAnalise engenheiroAnalise = new EngenheiroAnalise();
        BeanUtils.copyProperties(engenheiroAnaliseDTO, engenheiroAnalise);
        repository.save(engenheiroAnalise);
    }

    @Override
    public List<VoEngenheirosAnalise> findAllVoEngenheirosAnalise(EngenheiroAnaliseDTO engenheiroAnaliseDTO) {
        VoEngenheirosAnalise voEngenheirosAnalise = Objects.isNull(engenheiroAnaliseDTO) ? new VoEngenheirosAnalise() : new VoEngenheirosAnalise(engenheiroAnaliseDTO.matricula(), engenheiroAnaliseDTO.nome(), engenheiroAnaliseDTO.ativo());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<VoEngenheirosAnalise> example = Example.of(voEngenheirosAnalise, exampleMatcher);
        List<VoEngenheirosAnalise> engenheiros = engenheirosAnaliseRepository.findAll(example);
        engenheiros.sort(Comparator.comparing(VoEngenheirosAnalise::getNome));
        return engenheiros;
    }
}
