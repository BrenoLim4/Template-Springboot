package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.colaborador.Colaborador;
import br.gov.ce.sop.convenios.model.entity.convenio.colaborador.ColaboradorExtensao;
import br.gov.ce.sop.convenios.model.enums.TipoColaborador;
import br.gov.ce.sop.convenios.model.repository.convenio.colaborador.ColaboradorExtensaoRepository;
import br.gov.ce.sop.convenios.model.repository.convenio.colaborador.ColaboradorRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.ColaboradorService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.PrefeituraService;
import br.gov.ce.sop.convenios.utils.MaskUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log
public class ColaboradorServiceImpl implements ColaboradorService {

    private final ColaboradorExtensaoRepository colaboradorExtensaoRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final PrefeituraService prefeituraService;

    @Override
    public String cadastrarColaborador(ColaboradorDTO colaboradorDTO, TipoColaborador tipoColaborador) {
        Integer idPessoaJuridica = prefeituraService.getIdPessoa();
        colaboradorRepository.findColaboradorByIdPessoaJuridicaAndCpf(idPessoaJuridica, colaboradorDTO.cpf())
                .map(colaborador -> {
                    //reativar colaborador
                    colaborador.setNome(colaboradorDTO.nome());
                    colaborador.setEmail(colaboradorDTO.email());
                    colaborador.setAtivo(true);
                    log.info("Colaborador [" + colaborador.getNome() + "] reativado");
                    return colaborador;
                }).ifPresentOrElse(colaboradorRepository::save, () -> {
                    //cadastro de um novo colaborador
                    Colaborador colaborador = new Colaborador();
                    BeanUtils.copyProperties(colaboradorDTO, colaborador);
                    colaborador.setIdPessoaJuridica(idPessoaJuridica);
                    colaborador = colaboradorRepository.save(colaborador);
                    ColaboradorExtensao colaboradorExtensao = ColaboradorExtensao.builder()
                            .tipoColaborador(tipoColaborador)
                            .colaborador(colaborador)
                            .build();
                    colaboradorExtensaoRepository.save(colaboradorExtensao);
                });
        return "Colaborador " + colaboradorDTO.nome() + " cadastrado com sucesso.";
    }

    @Override
    public String editarColaborador(ColaboradorDTO colaboradorDTO) {
        Objects.requireNonNull(colaboradorDTO.id(), "Atributo id deve ser informado.");
        colaboradorRepository.updateNomeAndEmailById(colaboradorDTO.nome(), colaboradorDTO.email(), colaboradorDTO.id());
        return "Colabordor " + colaboradorDTO.nome() + " editado com sucesso.";
    }

    @Override
    public String inativarColaborador(Integer id) {
        colaboradorRepository.inativarColaborador(id);
        return "Colaborador inativado com sucesso";
    }

    @Override
    public List<ColaboradorDTO> findAllColaboradoresByPrefeitura() {
        Integer idPessoaJuridica = prefeituraService.getIdPessoa();
        List<Colaborador> colaboradores = colaboradorRepository.findAllByIdPessoaJuridicaAndAtivo(idPessoaJuridica, true);
        return colaboradores.stream()
                .peek(colaborador -> colaborador.setTipoColaborador(colaboradorExtensaoRepository.findIdTipoColaboradorByIdColaborador(colaborador.getId())))
                .map(colaborador -> new ColaboradorDTO(colaborador.getId(), MaskUtils.cpfMask(colaborador.getCpf(), '#'), colaborador.getNome(), colaborador.getEmail(), colaborador.getTipoColaborador()))
                .toList();
    }

    @Override
    public List<ColaboradorDTO> getCpfColaboradoresByPrefeituraAndTipoColaborador(Integer tipoColaborador) {
        Integer idPessoaJuridica = prefeituraService.getIdPessoa();
        List<Colaborador> colaboradores = colaboradorRepository.findAllByIdPessoaJuridicaAndAtivo(idPessoaJuridica, true);
        return  colaboradores.stream()
                .peek(colaborador -> colaborador.setTipoColaborador(colaboradorExtensaoRepository.findIdTipoColaboradorByIdColaborador(colaborador.getId())))
                .filter(colaborador -> Objects.equals(colaborador.getTipoColaborador(), tipoColaborador))
                .map(colaborador -> new ColaboradorDTO(colaborador.getId(), colaborador.getCpf(), colaborador.getNome(), colaborador.getEmail(), colaborador.getTipoColaborador()))
                .toList();
    }

    @Override
    public Optional<Colaborador> findByIdPrefeituraAndCpf(Integer idPrefeitura, String cpf) {
        return colaboradorRepository.findColaboradorByIdPessoaJuridicaAndCpf(idPrefeitura, cpf);
    }

}
