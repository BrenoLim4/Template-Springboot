package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.EngenheiroAnaliseDTO;
import br.gov.ce.sop.convenios.api.dto.parametros.RAtribuirEngenheiroAnalise;
import br.gov.ce.sop.convenios.model.entity.celebracao.EngenheiroAnalise;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoEngenheirosAnalise;

import java.util.List;

public interface EngenheiroAnaliseService {

    List<EngenheiroAnalise> findAllEngenheirosAnaliseAtivos();
    String atribuirAnalise(RAtribuirEngenheiroAnalise params);
    void cadastrar(EngenheiroAnaliseDTO params);
    void save (EngenheiroAnaliseDTO engenheiroAnaliseDTO);
    List<VoEngenheirosAnalise> findAllVoEngenheirosAnalise(EngenheiroAnaliseDTO engenheiroAnaliseDTO);
}
