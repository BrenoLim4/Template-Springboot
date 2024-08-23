package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAnaliseDocumentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoAnaliseDocumentosRepository extends JpaRepository<VoCelebracaoAnaliseDocumentos, Integer> {

    List<VoCelebracaoAnaliseDocumentos> findAllByIdEntidade(Integer idCelebracao);
    List<VoCelebracaoAnaliseDocumentos> findAllByIdEntidadeAndIdTipoAnalise(Integer idCelebracao, Integer idTipoAnalise);
}
