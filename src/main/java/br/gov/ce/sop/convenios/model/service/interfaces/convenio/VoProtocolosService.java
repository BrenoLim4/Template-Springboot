package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.filter.FilterProtocolosDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.VoProtocolos;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoProtocolosService extends BasicEntityService<VoProtocolos, Integer, VoProtocolos> {
    Page<VoProtocolos> findAllByQuery(FilterProtocolosDTO filter, Pageable pageable);

    VoProtocolos findDocumentoProtocoloByQuery(Integer idEntidade, Integer idTipoDocumento);
}
