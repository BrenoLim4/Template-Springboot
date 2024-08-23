package br.gov.ce.sop.convenios.model.repository.convenio;

import br.gov.ce.sop.convenios.model.entity.convenio.VoCadastrosConvenio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoCadastrosConvenioRepository extends JpaRepository<VoCadastrosConvenio, Integer> {
    List<VoCadastrosConvenio> findTop5ByIdConvenenteOrderByDataCadastroDesc(Integer IdConvenente);
}
