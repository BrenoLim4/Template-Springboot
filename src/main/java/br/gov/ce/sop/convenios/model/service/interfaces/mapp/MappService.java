package br.gov.ce.sop.convenios.model.service.interfaces.mapp;

import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoDocumentosEspelhoMapp;
import br.gov.ce.sop.convenios.model.entity.mapp.VoMappDetalhe;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface MappService extends BasicEntityService<Mapp, Integer, Mapp> {

    List<Mapp> buscarMapps(String codigoMapp);
    List<VoDocumentosEspelhoMapp> buscarMappsPorIdConvenio(Integer id);
    boolean possuiDocumentosAguardandoEnvio(Integer idConvenio);
    String desassociarMappConvenio(Integer idMapp, Long idDocumento);

    void saveMappsCovenio(List<Integer> idMapps, Convenio convenio);
    void verificarValidadeMapp(List<Integer> idMapps);
    List<VoMappDetalhe> buscarMappsByIdCelebracao(Integer idCelebracao);
    String buscarMappsConcatenadaosPorIdCelebracao(Integer idCelebracao);
}
