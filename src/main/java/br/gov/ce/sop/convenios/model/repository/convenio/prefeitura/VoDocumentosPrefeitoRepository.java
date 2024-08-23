package br.gov.ce.sop.convenios.model.repository.convenio.prefeitura;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDocumentosPrefeito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoDocumentosPrefeitoRepository extends JpaRepository<VoDocumentosPrefeito, Integer> {

    List<VoDocumentosPrefeito> findAllByCpfAndCnpj(String cpf, String cnpj);

    List<VoDocumentosPrefeito> findAllByIdPrefeitura(Integer id);

    List<VoDocumentosPrefeito> findAllByCnpj(String cnpj);

    Optional<VoDocumentosPrefeito> findVoDocumentosPrefeitosByIdDocumento(Integer idDocumento);
}
