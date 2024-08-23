package br.gov.ce.sop.convenios.webClient;

import br.gov.ce.sop.convenios.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


@Service
@RequiredArgsConstructor
@Log
public class RequestServiceImpl implements RequestService {

    private final WebClientService webClientService;

    @Override
    public <T> List<T> requestToListModel(Class<T> model, Supplier<Request> request) {
        String endPoint = request.get().endPoint();
        log.info("Iniciando requisição no endpoint [" + endPoint + "]");
        long tempoInicial = System.currentTimeMillis();
        //--------------------------ONDE A MÁGICA ACONTECE-----------------------------------------------------------
        List<T> lista = buildRequest(request.get())
                .bodyToFlux(model)
                .collectList()
                .block();
        //----------------------------------------------------------------------------------------------------------
        log.info("Requisição do endpoint [" + endPoint + "] finalizada(o). Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + " ms.");
        return lista;
    }

    @Override
    public <T> T requestToUniqueModel(Class<T> model, Supplier<Request> request) {
        return buildRequest(request.get()).bodyToMono(model).block();
    }

    @Override
    public void requestWithoutReturn(Supplier<Request> requestSupplier) {
        buildRequest(requestSupplier.get()).bodyToMono(Void.class).block();
    }

    private WebClient.ResponseSpec buildRequest(Request request) {
        final Map<String, Object> bodyRequest = request.bodyRequestObject() != null ? Utils.mapObjectToHashMap(request.bodyRequestObject()) : request.bodyRequest();
        return webClientService.api(request.method(), request.baseUrl(), request.endPoint(), bodyRequest, request.requestParams(), request.pathVariables());
    }
}
