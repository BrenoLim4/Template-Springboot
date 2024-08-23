package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.HistoricoCelebracao;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoCelebracao;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface HistoricoCelebracaoService extends BasicEntityService<HistoricoCelebracao, Integer, HistoricoCelebracao> {

    void salvar(HistoricoCelebracao historicoCelebracao);
    void salvar(Integer idCelebracao, TipoHistoricoCelebracao tipoHistorico, String observacao);
}
