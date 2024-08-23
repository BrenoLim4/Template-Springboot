package br.gov.ce.sop.convenios.model.service.interfaces.documentodigital;

import br.gov.ce.sop.convenios.model.entity.documentodigital.TipoDocumentoXTipoAssinador;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.List;

public interface TipoDocumentoXTipoAssinadorService extends BasicEntityService<TipoDocumentoXTipoAssinador, Integer, TipoDocumentoXTipoAssinador> {
    List<Integer> findAllByIdTipoDocumento(Integer idTipoDocumento);

    Boolean findOneByIdDocumentoIdTipoAssinadorAndNotSigned(Integer idDocumento, Integer idTipoAssinador);
}
