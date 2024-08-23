package br.gov.ce.sop.convenios.model.service.impl.convenio;

import br.gov.ce.sop.convenios.api.dto.parametros.RCadastrarPublicacao;
import br.gov.ce.sop.convenios.api.dto.parametros.RParamsUploadDocumento;
import br.gov.ce.sop.convenios.api.dto.parametros.RSolicitarConvenio;
import br.gov.ce.sop.convenios.api.exception.WarningException;
import br.gov.ce.sop.convenios.model.dto.ConvenioDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import br.gov.ce.sop.convenios.model.entity.convenio.*;
import br.gov.ce.sop.convenios.model.enums.TipoOrigemConvenio;
import br.gov.ce.sop.convenios.model.repository.convenio.ConvenioRepository;
import br.gov.ce.sop.convenios.model.service.interfaces.EmailService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.CelebracaoService;
import br.gov.ce.sop.convenios.model.service.interfaces.convenio.*;
import br.gov.ce.sop.convenios.model.service.interfaces.documentodigital.DocumentoDigitalService;
import br.gov.ce.sop.convenios.model.service.interfaces.mapp.MappService;
import br.gov.ce.sop.convenios.model.service.interfaces.planotrabalho.PlanoTrabalhoService;
import br.gov.ce.sop.convenios.webClient.Request;
import br.gov.ce.sop.convenios.webClient.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class ConvenioServiceImpl implements ConvenioService {

    private final ConvenioRepository repository;
    private final HistoricoConvenioService historicoConvenioService;
    private final CelebracaoService celebracaoService;
    private final DocumentoDigitalService documentoDigitalService;
    private final MappService mappService;
    private final PrefeituraService prefeituraService;
    private final PrefeitoService prefeitoService;
    private final EmailService emailService;
    private final RequestService requestService;
    private final PlanoTrabalhoService planoTrabalhoService;
    @Value("${base-url-api.webservice-sefaz}")
    private String WsSefazBaseUrl;

    @Override
    public List<Convenio> getEntities(Convenio filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Convenio> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @Override
    public List<Convenio> getAll() {
        return repository.findAll();
    }

    @Override
    public Convenio getEntityById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void solicitarNovoConvenio(RSolicitarConvenio params) {
        Celebracao celebracao = concluirCadastroNovoConvenio(params);
        try {
            uploadOficioCelebracao(params.oficioCelebracao(), celebracao.getId());
            emailService.sendMailNovoConvenioSolicitado(celebracao.getId(), params.oficioCelebracao());
        } catch (Exception e) {
            repository.delete(celebracao.getConvenio());//fazer rollback caso não consiga gravar o documento
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConvenioDTO buscarConvenioSefaz(int nrSacc, int idConvenio) {
        Convenio convenio = repository.findById(idConvenio).orElseThrow();
        int exercicio = convenio.getDataInclusao().getYear();
        return requestService.requestToUniqueModel(ConvenioDTO.class, Request.builder()
                .baseUrl(WsSefazBaseUrl)
                .method(HttpMethod.GET)
                .endPoint("/convenio/" + exercicio + "/" + nrSacc)
                ::build
        );
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void cadastrarPublicacao(RCadastrarPublicacao params) {
        /*
        * atualizar dados do Convênio
        * atualizar status da celebração
        * atualizar dados do plano de trabalho
        * gravar historicos do convênio e da celebração
        * realizar upload do documento da publicação do Diário Oficial
        * */
        verificarCadastroPublicacao(params);
        Convenio convenio = repository.findById(params.idConvenio()).orElseThrow();
        BeanUtils.copyProperties(params, convenio);
        convenio.setValorTotal(params.valor());
        convenio.setStatusConvenio(new StatusConvenio(StatusConvenio.VIGENTE));
        repository.save(convenio);
        historicoConvenioService.publicado(params);
        celebracaoService.celebracaoFinalizada(convenio.getId());
        planoTrabalhoService.atualizar(params);
        uploadPublicacao(params.anexoPublicacao(), params.idConvenio());
    }

    private void verificarCadastroPublicacao(RCadastrarPublicacao params){
        LocalDate now = LocalDate.now();
        List<String> warnings = new ArrayList<>();
        if (params.dataAssinatura().isAfter(now))
            warnings.add("Data assinatura não pode ser maior que a data atual!");
        if (params.dataPublicacao().isAfter(now))
            warnings.add("Data da publicação não pode ser maior que a data atual!");
        if(params.dataAssinatura().isAfter(params.dataPublicacao()))
            warnings.add("Data da publicação não pode ser maior que a data da assinatura!");
        if(params.dataFimVigencia().compareTo(params.dataPublicacao()) < 1)
            warnings.add("Data fim vigência não pode ser menor ou igual que a data da publicação!");
        if (params.valor().compareTo(BigDecimal.ZERO) < 1)
            warnings.add("Valor Total tem que ser maior que zero.");
        if(!params.valor().equals(params.valorConcessao().add(params.valorContrapartida())))
            warnings.add("A soma dos valores da concessão e da contrapartida tem que ser igual ao valor total!");
        if (repository.existsByNrSacc(params.nrSacc()))
            warnings.add("O Número do SACC " + params.nrSacc() + " já está cadastrado em outro convênio!");
        if(!warnings.isEmpty())
            throw new WarningException(warnings);
    }

    @Transactional
    public Celebracao concluirCadastroNovoConvenio(RSolicitarConvenio params) {
        mappService.verificarValidadeMapp(params.idMapps());
        Convenio convenio = saveConvenio(params.objeto(), params.idTipo());
        Celebracao celebracao = celebracaoService.inserirNovaCelebracao(convenio);
        mappService.saveMappsCovenio(params.idMapps(), convenio);
        return celebracao;
    }

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void uploadOficioCelebracao(String base64, Integer idCelebracao) {
        RParamsUploadDocumento body = RParamsUploadDocumento.builder()
                .base64(base64)
                .idEntidade(idCelebracao.toString())
                .idTipoDocumento(41001)
                .observacao("Ofício com Solicitação de Formalização Convênio - (ID" + String.format("%06d", idCelebracao) + " Celebração)")
                .build();
        documentoDigitalService.uploadDocumento(body);// documento de ofício de abertura de convênio.
    }

    @Transactional
    public void uploadPublicacao(String base64, Integer idConvenio) {
        RParamsUploadDocumento body = RParamsUploadDocumento.builder()
                .base64(base64)
                .idEntidade(idConvenio.toString())
                .idTipoDocumento(41039)
                .observacao("Anexo da Publicação do Convênio no DOE - (ID" + String.format("%06d", idConvenio) + " Convênio)")
                .build();
        documentoDigitalService.uploadDocumento(body);// documento de publicação do Convênio no Diário Oficial.
    }

    //    @Transactional
    public Convenio saveConvenio(String objeto, Integer idTipo) {
        Convenio convenio = Convenio.builder()
                .statusConvenio(new StatusConvenio(StatusConvenio.AGUARDANDO_CELEBRACAO))
                .objeto(objeto)
                .origem(TipoOrigemConvenio.RODOVIAS)
                .dataInclusao(LocalDateTime.now())
                .tipoConvenio(new TipoConvenio(idTipo))
                .convenente(prefeituraService.findPrefeituraLogada())
                .prefeito(prefeitoService.findPrefeitoAtivoByPrefeitura())
                .build();
        convenio = repository.save(convenio);
        historicoConvenioService.incluido(convenio);
        return convenio;
    }

}
