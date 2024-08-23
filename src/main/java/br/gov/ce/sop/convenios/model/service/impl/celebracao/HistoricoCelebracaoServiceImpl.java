package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoCelebracao;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoCelebracao;
import br.gov.ce.sop.convenios.model.repository.celebracao.HistoricoCelebracaoRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.HistoricoCelebracaoService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoCelebracaoServiceImpl implements HistoricoCelebracaoService {

    private final HistoricoCelebracaoRepository repository;
    private final TokenService tokenService;

    @Override
    public List<HistoricoCelebracao> getEntities(HistoricoCelebracao filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<HistoricoCelebracao> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<HistoricoCelebracao> getAll() {
        return repository.findAll();
    }

    @Override
    public HistoricoCelebracao getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void salvar(HistoricoCelebracao historicoCelebracao) {
        repository.save(historicoCelebracao);
    }

    @Override
    public void salvar(Integer idCelebracao, TipoHistoricoCelebracao tipoHistorico, String observacao) {
        HistoricoCelebracao historicoCelebracao = HistoricoCelebracao.builder()
                .idCelebracao(idCelebracao)
                .tipoHistoricoCelebracao(tipoHistorico)
                .observacao(observacao)
                .dataHora(LocalDateTime.now())
                .matricula(TokenService.getTokenUsername())
                .nomeUsuario(tokenService.getNomeCompleto())
                .build();
        repository.save(historicoCelebracao);
    }
}
