package br.gov.ce.sop.convenios.model.entity.celebracao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "status_celebracao", schema = "convenios")
public class StatusCelebracao implements Serializable {

    public static int AGUARDANDO_PROTOCOLO = 0;
    public static int AGUARDANDO_ENVIO_DOCUMENTOS = 1;
    public static int AGUARDANDO_ANALISE_DOCUMENTOS = 2;
    public static int AGUARDANDO_CERTIDAO_REGULARIDADE = 3;
    public static int AGUARDANDO_ANALISE_JURIDICO = 4;
    public static int AGUARDANDO_FINALIZACAO_EPARCERIAS = 5;
    public static int AGUARDANDO_PUBL_DIARIO_OFICIAL = 6;
    public static int PUBLICADA = 7;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String descricao;

    @Column(length = 3)
    @NotNull
    private String sigla;

    @Column
    private String cor;


    public StatusCelebracao(Integer id) {
        this.id = id;
    }

    public StatusCelebracao() {
    }

    public StatusCelebracao(Integer id, String descricao, String sigla, String cor) {
        this.id = id;
        this.descricao = descricao;
        this.sigla = sigla;
        this.cor = cor;
    }
}