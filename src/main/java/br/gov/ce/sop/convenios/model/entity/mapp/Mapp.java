package br.gov.ce.sop.convenios.model.entity.mapp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mapp", schema = "webservice_seplag")
public class  Mapp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_mapp")
    private String codigoMapp;

    @Column(name = "mapp")
    private String objeto;

    @Column(name = "status")
    private String status;

}
