package br.gov.ce.sop.convenios.api.dto.filter;

import br.gov.ce.sop.convenios.utils.StringDeserializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;


@Data
public class FilterCelebracaoAdministrativoDTO{
        @JsonView({View.ConsultaAdministrativo.class})
        private String cnpjConvenente;
        @JsonView({View.ConsultaAdministrativo.class})
        private String nrProtocolo;
        @JsonView({View.ConsultaAdministrativo.class})
        @JsonDeserialize(using = StringDeserializer.class)
        private String convenente;
        @JsonView({View.ConsultaAdministrativo.class})
        private String mapps;
        @JsonView({View.ConsultaAdministrativo.class})
        private List<Integer> status;
        @JsonDeserialize(using = StringDeserializer.class)
        @JsonView({View.ConsultaAdministrativo.class})
        private String objeto;

    public interface View{
        interface ConsultaAdministrativo{}
    }
}
