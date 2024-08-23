package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.documentodigital.VoCelebracaoDocumentosGeralDetalhes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoDocumentosGeralDetalhesRepository extends JpaRepository<VoCelebracaoDocumentosGeralDetalhes, Integer> {

    List<VoCelebracaoDocumentosGeralDetalhes> findAllByIdCelebracao(Integer idCelebracao);
}
