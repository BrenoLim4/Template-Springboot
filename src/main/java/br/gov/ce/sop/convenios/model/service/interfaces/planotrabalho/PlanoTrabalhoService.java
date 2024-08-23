package br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho;

import br.gov.ce.sop.convenios.api.dto.parametros.PlanoTrabalhoDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.model.entity.planotrabalho.PlanoTrabalho;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

public interface PlanoTrabalhoService extends BasicEntityService<PlanoTrabalho, Integer, PlanoTrabalho> {

    String cadastrar(PlanoTrabalhoDTO planoTrabalhoDTO);

    boolean existsByIdConvenio(Integer idConvenio);

    PlanoTrabalhoDTO findByIdConvenio(Integer idConvenio);

    void atualizar(RCadastrarPublicacao params);
}
