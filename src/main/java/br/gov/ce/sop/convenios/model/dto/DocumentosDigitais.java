package br.gov.ce.sop.convenios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosDigitais implements Serializable {

    private Long id;
    private String pathArquivo;
    private String nomeArquivo;
    private String versaoArquivo;
    private LocalDateTime dataHoraUpload;
    private Integer idTipoArquivo;
    private Integer idTipoDocumento;
    private String idEntidade;
    private Integer idEntidadeDoc;
    private String observacao;
    private String ocorrencia;
    private String idCarimbador;
    private Integer idTipoEntidadeColeta;
    private String idEntidadeColeta;
    private Integer idAssinado;
    private Integer idConferido;

}
