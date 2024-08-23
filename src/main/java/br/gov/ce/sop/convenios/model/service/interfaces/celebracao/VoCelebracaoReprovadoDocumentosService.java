package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.CelebracaoReprovadoDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovadoDocumentos;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface VoCelebracaoReprovadoDocumentosService extends BasicEntityService<VoCelebracaoReprovadoDocumentos, Integer, VoCelebracaoReprovadoDocumentos> {
    List<CelebracaoReprovadoDocumentosDTO> findDocumentosReprovado(Integer idCelebracao);
}
