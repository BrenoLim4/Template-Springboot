package br.gov.ce.sop.convenios.model.repository.celebracao.view;

import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoAguardandoParecerDocumentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCelebracaoAguardandoParecerDocumentosRepository extends JpaRepository<VoCelebracaoAguardandoParecerDocumentos, Integer> {
    List<VoCelebracaoAguardandoParecerDocumentos> findAllByIdEntidade(Integer idCelebracao);
}
