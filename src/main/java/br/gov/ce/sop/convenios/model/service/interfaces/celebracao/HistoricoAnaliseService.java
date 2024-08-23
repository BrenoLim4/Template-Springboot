package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoAnalise;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface HistoricoAnaliseService extends BasicEntityService<HistoricoAnalise, Integer, HistoricoAnalise> {

    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoGeracao(Analise analise);

    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoEngenheiroAtribuido(Analise analise);

    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoEngenheiroSubstituido(Analise analise);

    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoAnaliseIniciada(Analise analise);
    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoAprovacao(Analise analise);
    @Transactional(propagation = Propagation.MANDATORY)
    void salvarHistoricoRejeicao(Analise analise);
}
