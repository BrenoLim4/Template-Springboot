package br.gov.ce.sop.convenios.model.repository.mapp;

import br.gov.ce.sop.convenios.model.entity.mapp.VoDocumentosEspelhoMapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoDocumentosEspelhoMappRepository extends JpaRepository<VoDocumentosEspelhoMapp, Integer> {

    List<VoDocumentosEspelhoMapp> findAllByIdConvenio(Integer id);
}
