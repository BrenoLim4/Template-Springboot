package br.gov.ce.sop.convenios.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoHistoricoConvenio {

    CONVENIO_INCLUIDO(0),
    MAPP_DESASSOCIADO(1),
    CELEBRACAO_FINALIZADA(2),
    CANCELADO(3);
    private final Integer id;

}
