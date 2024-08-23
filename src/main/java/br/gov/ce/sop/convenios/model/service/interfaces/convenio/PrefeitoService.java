package br.gov.ce.sop.convenios.model.service.interfaces.convenio;

import br.gov.ce.sop.convenios.api.dto.ColaboradorDTO;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.Prefeito;
import br.gov.ce.sop.convenios.model.entity.convenio.prefeitura.VoDocumentosPrefeito;

import java.util.List;

public interface PrefeitoService {
    void cadastrarPrefeito(ColaboradorDTO colaboradorDTO);

    Prefeito findPrefeitoAtivoByPrefeitura();
    Prefeito findByCpfAndCnpjPrefeitura(String cpf, String cnpjPrefeitura);

    List<VoDocumentosPrefeito> getDocumentosPrefeitoByPrefeituraLogada();

    List<VoDocumentosPrefeito> getDocumentosPrefeitoByIdPrefeitura(Integer idPrefeitura);
    List<VoDocumentosPrefeito> getDocumentosPrefeitoByCnpjPrefeitura(String cnpj);

    void rejeitarDocumento(Integer idDocumento, String comentario);
    void conferirDocumento(Integer idDocumento);
    void checarDocumentosFinalizarEnvio();

    void atualizarDados(ColaboradorDTO colaboradorDTO);
    boolean possuiDocumentosAguardandoConferencia(String cnpj);
    boolean possuiDocumentosRejeitados();
    boolean possuiDocumentosRejeitadosByCnpj(String cnpj);
    boolean existeAlgumaPendenciaDocumentos(String cnpj);
    boolean possuiDocumentosAguardandoEnvio(String cnpj);
}
