package br.gov.ce.sop.convenios.model.repository.documentodigital;

import br.gov.ce.sop.convenios.model.entity.documentodigital.TipoDocumentoXTipoAssinador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDocumentoXTipoAssinadorRepository extends JpaRepository<TipoDocumentoXTipoAssinador, Integer> {
    List<TipoDocumentoXTipoAssinador> findAllByIdTipoDocumento(Integer idTipoDocumento);
}
