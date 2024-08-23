package br.gov.ce.sop.convenios.model.service.impl.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoAnalise;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoAnalise;
import br.gov.ce.sop.convenios.model.repository.celebracao.HistoricoAnaliseRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.HistoricoAnaliseService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoAnaliseServiceImpl implements HistoricoAnaliseService {

    private final HistoricoAnaliseRepository repository;
    private final TokenService tokenService;

    @Override
    public List<HistoricoAnalise> getEntities(HistoricoAnalise filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<HistoricoAnalise> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<HistoricoAnalise> getAll() {
        return repository.findAll();
    }

    @Override
    public HistoricoAnalise getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void salvarHistoricoGeracao(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ANALISE_INSERIDA, "SISTEMA", "Inserida automáticamente. tipo: " + analise.getTipo().name(), "Usuário interno do Sistema");
    }

    @Override
    public void salvarHistoricoEngenheiroAtribuido(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ENGENHEIRO_ATRIBUIDO, TokenService.getTokenUsername(), "Engenheiro responsável pela análise dos documentos atribuido.", tokenService.getNomeCompleto());
    }

    @Override
    public void salvarHistoricoEngenheiroSubstituido(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ENGENHEIRO_SUBSTITUIDO, TokenService.getTokenUsername(), "Engenheiro responsável pela análise dos documentos substituído.", tokenService.getNomeCompleto());
    }

    @Override
    public void salvarHistoricoAnaliseIniciada(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ANALISE_INICIADA, TokenService.getTokenUsername(), "Processo de conferência dos documentos foi iniciada.", tokenService.getNomeCompleto());
    }

    @Override
    public void salvarHistoricoAprovacao(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ANALISE_APROVADA, TokenService.getTokenUsername(), "Analise aprovada.", tokenService.getNomeCompleto());
    }

    @Override
    public void salvarHistoricoRejeicao(Analise analise) {
        gravarHistorico(analise, TipoHistoricoAnalise.ANALISE_REJEITADA, TokenService.getTokenUsername(), "Analise reprovada.", tokenService.getNomeCompleto());
    }

    private void gravarHistorico(Analise analise, TipoHistoricoAnalise tipoHistorico, String matricula, String observacao, String nomeUsuario){
        HistoricoAnalise historicoAnalise = HistoricoAnalise.builder()
                .idAnalise(analise.getId())
                .dataHora(LocalDateTime.now())
                .tipoHistoricoAnalise(tipoHistorico)
                .matricula(matricula)
                .observacao(observacao)
                .nomeUsuario(nomeUsuario)
                .build();
        repository.save(historicoAnalise);
    }
}
