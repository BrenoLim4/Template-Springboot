package br.gov.ce.sop.convenios.model.entity.convenio.colaborador;

import br.gov.ce.sop.convenios.model.enums.TipoColaborador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "colaborador_extensao", schema = "convenios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorExtensao implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_colaborador", nullable = false, referencedColumnName = "id")
    private Colaborador colaborador;
    @Enumerated
    @Column(name = "id_tipo_colaborador")
    private TipoColaborador tipoColaborador;
}
