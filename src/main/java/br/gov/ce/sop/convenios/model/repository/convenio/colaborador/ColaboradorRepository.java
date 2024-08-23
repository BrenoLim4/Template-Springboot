package br.gov.ce.sop.convenios.model.repository.convenio.colaborador;

import br.gov.ce.sop.convenios.model.entity.convenio.colaborador.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    Optional<Colaborador> findColaboradorByIdPessoaJuridicaAndCpf(Integer idPessoaJuridica, String cpf);
    List<Colaborador> findAllByIdPessoaJuridicaAndAtivo(Integer idPessoaJuridica, boolean ativo);
    @Modifying
    @Query(value = "update Colaborador c set c.ativo = false where c.id = ?1")
    @Transactional
    void inativarColaborador(Integer id);


    @Query(value = "update Colaborador set nome = :nome, email = :email where id = :id")
    @Modifying
    @Transactional
    void updateNomeAndEmailById(String nome, String email, Integer id);
}
