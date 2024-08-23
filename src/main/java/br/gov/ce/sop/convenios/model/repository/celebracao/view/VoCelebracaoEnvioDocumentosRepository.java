package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoEnvioDocumentos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoCelebracaoEnvioDocumentosRepository extends JpaRepository<VoCelebracaoEnvioDocumentos, Integer> {
    Page<VoCelebracaoEnvioDocumentos> findAllByIdCelebracao(Integer idCelebracao, Pageable pageable);
}
