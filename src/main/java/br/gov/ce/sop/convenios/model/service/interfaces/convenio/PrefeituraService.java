package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.AssessoriaDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeitura;

public interface PrefeituraService {

    Prefeitura findPrefeituraLogada();

    Integer getIdPessoa();

    AssessoriaDTO cadastrarAssessoria(AssessoriaDTO assessoria);

    AssessoriaDTO getAssessoriaLogada();

    String desassociarAssessoria();

}
