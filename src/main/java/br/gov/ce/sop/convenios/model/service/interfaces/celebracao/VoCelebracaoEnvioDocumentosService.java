package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.CelebracaoEnvioDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvioDocumentos;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoCelebracaoEnvioDocumentosService extends BasicEntityService<VoCelebracaoEnvioDocumentos, Integer, VoCelebracaoEnvioDocumentos> {
    Page<VoCelebracaoEnvioDocumentos> buscarDocumentosEnvio(CelebracaoEnvioDocumentosDTO params, Pageable pageable);
}
