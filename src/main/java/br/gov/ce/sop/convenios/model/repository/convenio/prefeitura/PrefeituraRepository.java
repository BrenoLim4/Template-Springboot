package br.gov.ce.sop.convenios.model.repository.convenio.prefeitura;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrefeituraRepository extends JpaRepository<Prefeitura, Integer> {

    Optional<Prefeitura> findByPessoaJuridica_CnpjAndPessoaJuridica_IdTipoPessoaJuridica(String cnpj, Integer idTipo);
}
