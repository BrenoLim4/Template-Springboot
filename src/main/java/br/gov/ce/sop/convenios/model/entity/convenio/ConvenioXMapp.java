package br.gov.ce.sop.convenios.model.entity.convenio;

import br.gov.ce.sop.convenios.model.entity.mapp.Mapp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "convenio_x_mapp", schema = "convenios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioXMapp implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_convenio", nullable = false)
    private Convenio convenio;

    @ManyToOne
    @JoinColumn(name = "id_mapp", nullable = false)
    private Mapp mapp;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "status")
    private String status;

    public ConvenioXMapp(Convenio convenio, Mapp mapp){
        this.convenio = convenio;
        this.mapp = mapp;
        this.objeto = mapp.getObjeto();
        this.status = mapp.getStatus();
    }

    public ConvenioXMapp(Convenio convenio) {
        this.convenio = convenio;
    }
}
