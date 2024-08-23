package br.gov.ce.sop.convenios.model.repository.convenio.prefeitura;

import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrefeitoRepository extends JpaRepository<Prefeito, Integer> {

    List<Prefeito> findAllByCpf(String cpf);
    Optional<Prefeito> findPrefeitoByCpfAndPrefeitura_PessoaJuridica_Cnpj(String cpf, String cnpj);

    Optional<Prefeito> findByPrefeitura_IdPessoaAndAtivoIsTrue(Integer idPessoa);

    @Query(nativeQuery = true, value = "update convenios.prefeito p set ativo = false where p.id_prefeitura = ?1 and p.ativo")
    @Modifying
    void desativarPrefeitoAnterior(Integer idPrefeitura);
}
