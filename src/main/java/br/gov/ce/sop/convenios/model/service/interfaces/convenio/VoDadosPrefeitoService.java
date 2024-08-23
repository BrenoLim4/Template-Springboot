package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.filter.FilterDadosPrefeitoDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDadosPrefeito;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface VoDadosPrefeitoService extends BasicEntityService<VoDadosPrefeito, Integer, FilterDadosPrefeitoDTO> {
}
