package br.gov.ce.sop.convenios.model.service.interfaces.celebracao;

import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.AnaliseXDocumento;
import br.gov.ce.sop.convenios.model.enums.StatusCelebracao;
import br.gov.ce.sop.convenios.model.enums.TipoAnalise;
import br.gov.ce.sop.convenios.model.service.interfaces.BasicEntityService;

import java.util.Optional;

public interface AnaliseService extends BasicEntityService<Analise, Integer, Analise> {

    //Analises do administrativo e engenharia criadas após a finalização do envio dos documentos
    void gerarAnalisesPosEnvio(Integer idCelebracao);

    /*
    * versionar documentos quando forem substituídos pela primeira vez - passar de rejeitado para aguardando conferência
    * salvar id do documento antigo
    * excluir comentários quando finalizar reenvio da documentação
    * adicionar id da analise anterior
    * Criar nova analise e copiar itens da tabela analise_x_documentos da analise anterior
    * */
//    void copiarAnalise(Analise analise);
    void iniciarAnalise(AnaliseXDocumento analiseXDocumento);
    void conferirDocumento(Integer idDocumento, Integer idAnalise);

    void rejeitarDocumento(Integer idDocumento, String comentario, Integer idAnalise);
    String atribuirEngenheiro(String matricula, Integer idCelebracao);
    void aprovar(Analise analise);
    void rejeitar(Analise analise);
    void documentoCorrigido(Integer idDocumento, Integer idAnalise, Integer idTipoDocumento, String ocorrencia);
    void correcaoDocumentosFinalizada(Integer idCelebracao);
    StatusCelebracao verificarAnaliseParalela(Analise analise);
    Analise findById(Integer idAnalise);
    Optional<Analise> findByCelebracao_IdAndTipoAndAtualTrue(Integer idCelebracao, TipoAnalise tipoAnalise);
}
