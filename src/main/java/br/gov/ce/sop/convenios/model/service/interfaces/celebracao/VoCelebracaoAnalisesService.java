package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterAnaliseDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnalises;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoCelebracaoAnalisesService extends BasicEntityService<VoCelebracaoAnalises, Integer, VoCelebracaoAnalises> {
    Page<VoCelebracaoAnalises> findAllByQuery(FilterAnaliseDTO filter, Pageable pageable);
}
