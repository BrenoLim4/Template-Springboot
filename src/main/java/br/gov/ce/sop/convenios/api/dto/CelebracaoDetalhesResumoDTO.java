package br.gov.ce.sop.convenios.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebracaoDetalhesResumoDTO implements Serializable {

    private Integer id;

    private LocalDate dataInclusao;

    private String nrProtocolo;

    private String idConvenente;

    private String convenente;

    private String cnpjConvente;

    private String prefeito;

    private String cpfPrefeito;

    private String objeto;

    private String status;

    private List<String> mapps;

}
