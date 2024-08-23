package br.gov.ce.sop.convenios.model.repository.celebracao;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.DocumentoPrefeitoSituacaoRejeitado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DocumentoPrefeitoSituacaoRejeitadoRepository extends JpaRepository<DocumentoPrefeitoSituacaoRejeitado, Integer> {

    Optional<DocumentoPrefeitoSituacaoRejeitado> findByIdDocumento(Integer idDocumento);

    void deleteByIdDocumento(Integer idDocumento);
}
