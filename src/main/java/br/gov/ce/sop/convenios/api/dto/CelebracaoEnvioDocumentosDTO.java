package br.gov.ce.sop.convenios.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebracaoEnvioDocumentosDTO implements Serializable {
    private Integer idCelebracao;
}
