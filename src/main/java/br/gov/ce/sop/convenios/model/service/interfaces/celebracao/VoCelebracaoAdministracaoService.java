package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAdministrativoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAdministracao;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface VoCelebracaoAdministracaoService extends BasicEntityService<VoCelebracaoAdministracao, Integer, FilterCelebracaoAdministrativoDTO> {
}
