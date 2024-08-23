package br.gov.ce.sop.convenios.model.enums;

import java.util.Arrays;
import java.util.Objects;

public enum TipoAssinador {
    SUPERINTENDENTE(1),
    ADJUNTO_CONVENIOS(400),
    ENGENHARIA_CONVENIOS(401),
    FISCAL(402),
    COMISSAO_COVENIO(403),
    GERENCIA_JURIDICO(404),
    RESPONSAVEL_TECNICO(405),
    CONVENENTE(406),
    CONCEDENTE(407);

    public final Integer idTipoAssinador;

    TipoAssinador(Integer idTipoAssinador) {
        this.idTipoAssinador = idTipoAssinador;
    }

    public static TipoAssinador fromValue(Integer value) {
        return Arrays.stream(TipoAssinador.values()).filter(tipoAssinador -> Objects.equals(tipoAssinador.idTipoAssinador, value)).findFirst().orElseThrow();
    }

}
