package br.gov.ce.sop.convenios.model.repository.convenio.colaborador;

import br.gov.ce.sop.convenios.model.entity.convenio.colaborador.ColaboradorExtensao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColaboradorExtensaoRepository extends JpaRepository<ColaboradorExtensao, Integer> {

    ColaboradorExtensao findByColaborador_Id(Integer id);

    @Query(nativeQuery = true, value = "select c.id_tipo_colaborador from convenios.colaborador_extensao c where id_colaborador = ?1")
    Integer findIdTipoColaboradorByIdColaborador(Integer idColaborador);
}
