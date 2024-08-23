package br.gov.ce.sop.convenios.model.service.impl;

import br.gov.ce.sop.convenios.kafka.dto.SendMailMessage;
import br.gov.ce.sop.convenios.kafka.producer.KafkaProducer;
import br.gov.ce.sop.convenios.kafka.producer.ProducerRecord;
import br.gov.ce.sop.convenios.model.dto.email.*;
import br.gov.ce.sop.convenios.model.dto.email.params.InfoBaseConvenioDTO;
import br.gov.ce.sop.convenios.model.entity.celebracao.Analise;
import br.gov.ce.sop.convenios.model.entity.celebracao.Celebracao;
import br.gov.ce.sop.convenios.model.entity.celebracao.view.VoCelebracaoDetalheResumo;
import br.gov.ce.sop.convenios.model.enums.StatusAnalise;
import br.gov.ce.sop.convenios.model.enums.StatusCelebracao;
import br.gov.ce.sop.convenios.model.service.interfaces.EmailService;
import br.gov.ce.sop.convenios.model.service.interfaces.celebracao.VoCelebracaoDetalheResumoService;
import br.gov.ce.sop.convenios.security.service.TokenService;
import br.gov.ce.sop.convenios.webClient.Request;
import br.gov.ce.sop.convenios.webClient.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${base-url-api.mail-service}")
    private String BASE_URL;
    @Value("${convenio.front-url}")
    private String CONVENIO_FRONT_URL;
    @Value("${convenio.email-grupo}")
    private String GECOV_EMAIL;
    private final String EMAIL_PREFEITURA_TESTE = "desenv@sop.ce.gov.br";
    @Value("${spring.profiles.active}")
    private String PROFILE_ACTIVE;
    private final String ORIGEM_NOTIFICACAO = "SISTEMA CONVÊNIO: ";
    private final String REQUEST_MAPPING = "/email";
    private final RequestService requestService;
    private final KafkaProducer kafkaProducer;
    private final TokenService tokenService;
    private final VoCelebracaoDetalheResumoService voCelebracaoDetalheResumoService;

    @Override
    public void sendMail(SendMailDTO params) {
//        Link linkFooter = new Link(CONVENIO_FRONT_URL, "Clique aqui para ser redirecionado para o aplicativo do Convênios");
//        params.bodyMail().setLinkFooter(linkFooter);
        requestService.requestWithoutReturn(() -> Request.builder()
                .baseUrl(BASE_URL)
                .endPoint(REQUEST_MAPPING + "/send")
                .method(HttpMethod.POST)
                .bodyRequestObject(params)
                .build()
        );
    }

    @Override
    public void sendMailNovoConvenioSolicitado(Integer idCelebracao, String oficioCelebracao) {
        BodyMail bodyMail = buildBodyMail(idCelebracao, String.format("%s solicitou um novo Convênio", tokenService.getNomeCompleto()), "Solicitação encontra-se aguardando protocolo.", null);
        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO + "Novo Convênio Solicitado")
                        .destinatarios(Collections.singletonList(GECOV_EMAIL))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        .anexos(Collections.singletonList(AnexoDTO.builder()
                                        .base64(oficioCelebracao)
                                        .filename("oficio-celebração.pdf")
                                        .build())
                        )
                        ::build)
                ::build
        );
    }

    @Override
    public void sendMailCelebracaoProtocolada(Integer idCelebracao, String nrProtocolo) {
        BodyMail bodyMail = buildBodyMail(idCelebracao, "Processo de Celebração Protocolado via SUITE", "A celebração agora está aguardando o envio dos documentos.", StatusCelebracao.AGUARDANDO_ENVIO_DOCUMENTOS);

        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO+"Celebração Protocolada. " + nrProtocolo)
                        .destinatarios(getEmailPrefeitura(idCelebracao))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        ::build)
                ::build
        );
    }


    @Override
    public void sendMailDocumentosEnviados(Integer idCelebracao) {
        BodyMail bodyMail = buildBodyMail(idCelebracao, String.format("%s finalizou o envio da documentação.", tokenService.getNomeCompleto()), "A documentação básica está pronta para análise administrativa e aguarda a designação de um engenheiro.", StatusCelebracao.AGUARDANDO_ANALISE_DOCUMENTOS);
        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO+"Envio de Documentos Finalizado.")
                        .destinatarios(Collections.singletonList(GECOV_EMAIL))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        ::build)
                ::build
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMailCelebracaoConferida(Analise analise) {
        Celebracao celebracao = analise.getCelebracao();
        VoCelebracaoDetalheResumo celebracaoDetalhe = voCelebracaoDetalheResumoService.getEntityById(celebracao.getId());
        String titulo = String.format("%s  %s", analise.getTipo().getNome(), analise.getStatusAnalise().getNome());
        String mensagemPrincipal;
        if(analise.getStatusAnalise().equals(StatusAnalise.REPROVADA)){
            mensagemPrincipal = "Alguns documentos foram rejeitados. Esses documentos estão disponíveis para substituição na tela de correção do aplicativo de convênios.";
        }else {
            StringBuilder str = new StringBuilder();
            str.append("Todos os documentos da ").append(analise.getTipo().getNome().toLowerCase()).append(" foram aprovados.");
            if (celebracaoDetalhe.getIdStatus().equals(StatusCelebracao.AGUARDANDO_PARECER_GECOV.ordinal())){
                str.append(" O envio dos documentos complementares já está disponível.");
            }
            mensagemPrincipal = str.toString();
        }
        BodyMail bodyMail = buildBodyMail(celebracao.getId(), titulo, mensagemPrincipal, celebracao.getStatusCelebracao());
        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO + analise.getTipo().getNome() + " Finalizada.")
                        .destinatarios(getEmailPrefeitura(celebracao.getId()))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        ::build)
                ::build
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMailDocumentosCorrigidos(Integer idCelebracao) {
        BodyMail bodyMail = buildBodyMail(idCelebracao, String.format("%s finalizou a correção dos documentos rejeitados.", tokenService.getNomeCompleto()), "Os novos documentos já estão disponíveis para análise.", StatusCelebracao.AGUARDANDO_ANALISE_DOCUMENTOS);
        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO+"Correção dos Documentos Finalizada.")
                        .destinatarios(Collections.singletonList(GECOV_EMAIL))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        ::build)
                ::build
        );
    }

    @Override
    public void sendMailParecerCadastrado(Integer idCelebracao) {
        BodyMail bodyMail = buildBodyMail(idCelebracao, "Parecer da GECOV registrado", "O restante do processo de celebração será realizado dentro do sistema do SUITE.", StatusCelebracao.AGUARDANDO_PUBLICACAO);
        kafkaProducer.sendMessage(ProducerRecord.builder()
                .topico("send-mail")
                .message(SendMailMessage.builder()
                        .assunto(ORIGEM_NOTIFICACAO+"Parecer Registrado")
                        .destinatarios(getEmailPrefeitura(idCelebracao))
                        .cc(Collections.singletonList(GECOV_EMAIL))
                        .confirmacaoLeitura(true)
                        .bodyMail(bodyMail)
                        ::build)
                ::build
        );
    }

    @Override
    public void sendMailDocumentoRecusado() {

    }

    private List<String> getEmailPrefeitura(Integer idCelebracao){
        if (PROFILE_ACTIVE.equals("prod")){
            VoCelebracaoDetalheResumo celebracaoDetalhe = voCelebracaoDetalheResumoService.getEntityById(idCelebracao);
            return Objects.isNull(celebracaoDetalhe.getEmailAssessoria()) ? Collections.singletonList(celebracaoDetalhe.getEmailPrefeito()) : asList(celebracaoDetalhe.getEmailPrefeito(), celebracaoDetalhe.getEmailAssessoria());
        }
        return Collections.singletonList(EMAIL_PREFEITURA_TESTE);
    }

    private Map<String, String> buildDetails(Integer idCelebracao, StatusCelebracao statusCelebracao) {
        VoCelebracaoDetalheResumo celebracaoDetalhe = voCelebracaoDetalheResumoService.getEntityById(idCelebracao);
        InfoBaseConvenioDTO infoConvenio = InfoBaseConvenioDTO.builder()
                .mapps(celebracaoDetalhe.getMappsIds())
                .status(celebracaoDetalhe.getStatus())
                .objetoConvenio(celebracaoDetalhe.getObjeto())
                .nrProtocolo(celebracaoDetalhe.getNrProtocolo())
                .tipoObjeto(celebracaoDetalhe.getTipoConvenio())
                .build();
        Map<String, String> details = new LinkedHashMap<>();
        if (!Objects.isNull(infoConvenio.nrProtocolo())) details.put("Número Protocolo", infoConvenio.nrProtocolo());
        details.put("Objeto", infoConvenio.objetoConvenio());
        details.put("Tipo", infoConvenio.tipoObjeto());
        details.put("Mapp(s)", infoConvenio.mapps());
        details.put("Status", statusCelebracao != null ? statusCelebracao.getDescription() : infoConvenio.status());
        return details;
    }

    private BodyMail buildBodyMail(Integer idCelebracao, String titulo, String mensagemPrincipal, StatusCelebracao statusCelebracao) {
        Map<String, String> details = buildDetails(idCelebracao, statusCelebracao);
        return BodyMail.builder()
                .titulo(titulo)
                .mensagemPrincipal(mensagemPrincipal)
                .details(details)
                .build();
    }
}
