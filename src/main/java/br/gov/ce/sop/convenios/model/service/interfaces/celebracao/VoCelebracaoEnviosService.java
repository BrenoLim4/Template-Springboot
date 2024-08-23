package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterEnviosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvios;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoCelebracaoEnviosService extends BasicEntityService<VoCelebracaoEnvios, Integer, VoCelebracaoEnvios> {

    Page<VoCelebracaoEnvios> findAllByQuery(FilterEnviosDTO filter, Pageable pageable);
}
