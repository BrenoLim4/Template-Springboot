package br.gov.ce.sop.convenios.model.service.interfaces;

import br.gov.ce.sop.convenios.api.dto.PostRequestDTO;
import br.gov.ce.sop.convenios.api.dto.PostResponseDTO;

import java.util.List;

// C = Classe, T = Tipo do id, F = Classe do filtro (pode ser a própria classe ou diferente)
public interface BasicEntityService<C, ID, F> {

    List<C> getEntities(F filtro);

    List<C> getAll();

    C getEntityById(ID id);

    default PostResponseDTO<C> getPage(PostRequestDTO<F> postRequestDTO){throw new IllegalArgumentException("not yet implemented");}
}
