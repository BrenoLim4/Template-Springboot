package br.gov.ce.sop.convenios.model.repository.mapp;

import br.gov.ce.sop.convenios.model.entity.mapp.VoMappDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoMappDetalheRepository extends JpaRepository<VoMappDetalhe, String> {

    List<VoMappDetalhe> findAllByIdCelebracao(Integer idCelebracao);
}
