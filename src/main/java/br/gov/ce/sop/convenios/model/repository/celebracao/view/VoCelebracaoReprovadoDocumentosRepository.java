package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoReprovadoDocumentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoReprovadoDocumentosRepository extends JpaRepository<VoCelebracaoReprovadoDocumentos, Integer> {
    List<VoCelebracaoReprovadoDocumentos> findAllByIdCelebracao(Integer idCelebracao);
}