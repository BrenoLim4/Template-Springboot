package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.filter.FilterCelebracaoAguardandoParecerDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecer;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecerDocumentos;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface CelebracaoAguardandoParecerService extends BasicEntityService<VoCelebracaoAguardandoParecer, Integer, FilterCelebracaoAguardandoParecerDTO>{
    List<VoCelebracaoAguardandoParecerDocumentos> findAllDocumentsByIdCelebracao(Integer id);
}
