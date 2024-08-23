package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.convenio.ConvenioXMapp;
import br.gov.ce.sop.convenios.model.entity.convenio.HistoricoConvenio;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface HistoricoConvenioService extends BasicEntityService<HistoricoConvenio, Integer, HistoricoConvenio> {

    void gravarHistoricoMappDesassociado(ConvenioXMapp convenioXMapp);
    void incluido(Convenio convenio);
    void publicado(RCadastrarPublicacao publicacao);
    void gravarHistoricoCancelado(Integer id, String motivo);
}
