package br.gov.ce.sop.convenios.model.enums;

import lombok.Getter;

import java.util.Arrays;

import static br.gov.ce.sop.convenios.model.enums.TipoAssinador.CONVENENTE;
import static br.gov.ce.sop.convenios.model.enums.TipoAssinador.RESPONSAVEL_TECNICO;

@Getter
public enum TipoColaborador {
    PREFEITO(CONVENENTE), ENGENHEIRO(RESPONSAVEL_TECNICO);

    public final TipoAssinador tipoAssinador;

    TipoColaborador(TipoAssinador tipoAssinador) {
        this.tipoAssinador = tipoAssinador;
    }

    public static TipoColaborador valueOf(int ordinal){
        return Arrays.stream(values()).filter(tipoColaborador -> tipoColaborador.ordinal() == ordinal).findFirst().orElse(null);
    }
}
