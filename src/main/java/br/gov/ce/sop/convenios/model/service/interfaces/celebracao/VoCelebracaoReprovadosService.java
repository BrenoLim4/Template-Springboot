package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterReprovadoDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovados;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoCelebracaoReprovadosService extends BasicEntityService<VoCelebracaoReprovados, Integer, VoCelebracaoReprovados> {
    Page<VoCelebracaoReprovados> findAllByQuery(FilterReprovadoDTO filter, Pageable pageable);
}
