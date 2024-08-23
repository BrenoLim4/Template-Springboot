package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.api.dto.parametros.*;
import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import br.gov.ce.sop.convenios.model.entity.convenio.Convenio;
import br.gov.ce.sop.convenios.model.enums.TipoHistoricoCelebracao;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;
import br.gov.ce.sop.convenios.model.service.interfaces.Protocolavel;

public interface CelebracaoService extends BasicEntityService<Celebracao, Integer, Celebracao>, Protocolavel {
    Celebracao save(Celebracao celebracao);
    Celebracao inserirNovaCelebracao(Convenio convenio);
    String finalizarEnvioDocumentos(RFinalizarEnvio params);
    String finalizarCorrecaoDocumento(Integer idCelebracao);
    String finalizarAnalise(RFinalizarAnalise params);
    String finalizarCadastroParecer(RCadastrarParecerGecov params);
    String arquivar(RParamsArquivarCelebracao params);
    void gravarHistoricoCelebracao(Integer idCelebracao, TipoHistoricoCelebracao tipoHistorico, String observacao);
    String enviarFormAberturaConta(RParamsFormAberturaConta params);
    boolean existsByNrProtocolo(String nrProtocolo);
    void celebracaoFinalizada(Integer idConvenio);
}
