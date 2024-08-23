package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.CelebracaoAnaliseDocumentosDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnaliseDocumentos;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface VoCelebracaoAnaliseDocumentosService extends BasicEntityService<VoCelebracaoAnaliseDocumentos, Integer, VoCelebracaoAnaliseDocumentos> {
    List<CelebracaoAnaliseDocumentosDTO> findDocumentosParaAnalise(Integer idCelebracao, Integer idTipoAnalise);
}
