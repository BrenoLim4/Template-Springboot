package br.gov.ce.sop.convenios.model.entity.celebracao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tipo_historico_celebracao", schema = "convenios")
public class TipoHistoricoCelebracao implements Serializable {

    public static final int INCLUIDO_AUTOMATICAMENTE = 1;
    public static final int PROTOCOLADO = 2;
    public static final int ENVIO_DOCUMENTOS_FINALIZADOS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String descricao;

}