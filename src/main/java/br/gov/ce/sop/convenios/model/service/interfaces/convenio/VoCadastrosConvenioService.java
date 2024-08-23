package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.VoCadastrosConvenio;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface VoCadastrosConvenioService extends BasicEntityService<VoCadastrosConvenio, Integer, VoCadastrosConvenio>  {
    List<VoCadastrosConvenio> buscarUltimosCadastros(String idConvenente);
}
