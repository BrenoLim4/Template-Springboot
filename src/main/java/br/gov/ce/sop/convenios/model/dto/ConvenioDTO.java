package br.gov.ce.sop.convenios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConvenioDTO {

    private String codigo;
    private String numeroOriginal;
    private String codigoUnidadeGestora;
    private String descricaoObjeto;
//    private boolean ativo;
    private String cnpjCelebranteConvenio;

    private LocalDate dataCelebracao;
    private LocalDate dataPublicacao;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
//    private LocalDate dataRescisao;
//    private LocalDate dataPublicacaoRescisao;
//    private LocalDate dataConclusao;
//    private LocalDate dataLimitePrestacaoContas;
//    private LocalDate dataApresentacaoPrestacaoContas;
//    private LocalDate dataLimiteAprovacaoPrestacaoContas;
//    private LocalDate dataAprovacaoPrestacaoContas;
//    private LocalDate dataFimVigenciaTotal;

    private BigDecimal valorConcessao;
    private BigDecimal valorContrapartida;
    private BigDecimal valor;
    private BigDecimal valorTotalAditivos;
    private BigDecimal valorTotal;
}
