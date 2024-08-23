package br.gov.ce.sop.convenios.model.entity.mapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "vw_mapp_detalhes", schema = "convenios")
public class VoMappDetalhe {
    @Id
    @Column(name = "codigo_mapp", length = Integer.MAX_VALUE)
    private String codigoMapp;

    @Column(name = "id_celebracao")
    private Integer idCelebracao;

    @Column(name = "objeto", length = Integer.MAX_VALUE)
    private String objeto;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "programa", length = Integer.MAX_VALUE)
    private String programa;

    @Column(name = "valor_programado")
    private BigDecimal valorProgramado;

    @Column(name = "valor_solicitado")
    private BigDecimal valorSolicitado;

    @Column(name = "descricao_ultimo_acompanhamento", length = Integer.MAX_VALUE)
    private String descricaoUltimoAcompanhamento;

    @Column(name = "estagio_execucao", length = Integer.MAX_VALUE)
    private String estagioExecucao;

    @Column(name = "status_envio", length = Integer.MAX_VALUE)
    private String statusEnvio;

    @Column(name = "enviado")
    private Boolean enviado;

    @Column(name = "id_documento")
    private Integer idDocumento;
}