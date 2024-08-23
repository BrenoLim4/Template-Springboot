package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterConsultaCelebracaoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoConsulta;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface CelebracaoConsultaService extends BasicEntityService<VoCelebracaoConsulta, Integer, FilterConsultaCelebracaoDTO> {
}
