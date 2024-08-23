package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.colaborador.Colaborador;
import br.gov.ce.sop.convenios.model.enums.TipoColaborador;

import java.util.List;
import java.util.Optional;

public interface ColaboradorService {

    String cadastrarColaborador(ColaboradorDTO colaboradorDTO, TipoColaborador tipoColaborador);

    String inativarColaborador(Integer id);

    String editarColaborador(ColaboradorDTO colaboradorDTO);

    List<ColaboradorDTO> findAllColaboradoresByPrefeitura();

    List<ColaboradorDTO> getCpfColaboradoresByPrefeituraAndTipoColaborador(Integer tipoColaborador);
    Optional<Colaborador> findByIdPrefeituraAndCpf(Integer idPrefeitura, String cpf);
}
