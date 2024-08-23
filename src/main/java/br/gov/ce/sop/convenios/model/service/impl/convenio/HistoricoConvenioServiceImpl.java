package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.convenio.HistoricoConvenio;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoConvenio;
import br.gov.ce.sop.convenios.model.repository.convenio.HistoricoConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.HistoricoConvenioService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoConvenioServiceImpl implements HistoricoConvenioService {

    private final HistoricoConvenioRepository repository;

    @Override
    public List<HistoricoConvenio> getEntities(HistoricoConvenio filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<HistoricoConvenio> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<HistoricoConvenio> getAll() {
        return repository.findAll();
    }

    @Override
    public HistoricoConvenio getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void gravarHistoricoMappDesassociado(ConvenioXMapp convenioXMapp) {
        Convenio convenio = convenioXMapp.getConvenio();
        String observacao = String.format("Mapp de código %s07 desassociado do convênio %d", convenioXMapp.getMapp().getCodigoMapp(), convenio.getId());
        HistoricoConvenio historicoConvenio = build(observacao, TipoHistoricoConvenio.MAPP_DESASSOCIADO, convenio.getId());
        repository.save(historicoConvenio);
    }

    @Override
    public void incluido(Convenio convenio) {
        HistoricoConvenio convenioIncluido = build("Convênio Incluído", TipoHistoricoConvenio.CONVENIO_INCLUIDO, convenio.getId());
        repository.save(convenioIncluido);
    }

    @Override
    public void publicado(RCadastrarPublicacao publicacao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String observacao = String.format("Data da publicação %s no DOE e fim vigência previsto para %s", publicacao.dataPublicacao().format(formatter), publicacao.dataFimVigencia().format(formatter));
        HistoricoConvenio convenioIncluido = build(observacao, TipoHistoricoConvenio.CELEBRACAO_FINALIZADA, publicacao.idConvenio());
        repository.save(convenioIncluido);
    }

    @Override
    public void gravarHistoricoCancelado(Integer id, String motivo) {
        HistoricoConvenio historicoConvenio = build(motivo, TipoHistoricoConvenio.CANCELADO, id);
        repository.save(historicoConvenio);
    }

    private HistoricoConvenio build(String observacao, TipoHistoricoConvenio tipoHistoricoConvenio, Integer idConvenio){
        return HistoricoConvenio.builder()
                .tipoHistoricoConvenio(tipoHistoricoConvenio)
                .matricula(TokenService.getTokenUsername())
                .convenio(new Convenio(idConvenio))
                .dataHora(LocalDateTime.now())
                .observacao(observacao)
                .build();
    }
}
