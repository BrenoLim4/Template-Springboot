package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.api.dto.parametros.RSolicitarConvenio;
import br.gov.ce.sop.convenios.model.dto.ConvenioDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface ConvenioService extends BasicEntityService<Convenio, Integer, Convenio> {
    void solicitarNovoConvenio(RSolicitarConvenio params);
    ConvenioDTO buscarConvenioSefaz(int nrSacc, int idConvenio);
    void cadastrarPublicacao(RCadastrarPublicacao params);
}
