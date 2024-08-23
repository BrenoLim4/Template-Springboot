package br.gov.ce.sop.convenios.webClient;

import br.gov.ce.sop.convenios.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log
public class WebClientServiceImpl implements WebClientService {

    private final WebClient webClient;
    private final TokenService tokenService;

    @Override
    public WebClient.ResponseSpec api(HttpMethod method, String baseUrl, String endpoint, Map<String, ?> bodyParams, Map<String, List<String>> queryParams, String... pathVariables){
        return webClient
                .method(method)
                .uri(baseUrl, uriBuilder -> uriBuilder
                        .path(endpoint)
                        .queryParams(Objects.isNull(queryParams) ? new LinkedMultiValueMap<>() : new LinkedMultiValueMap<>(queryParams))
                        .build(pathVariables == null ? new Object[]{""} : pathVariables)
                )
                .header(HttpHeaders.AUTHORIZATION, TokenService.getAuthorization())
                .body(BodyInserters.fromValue(bodyParams != null ? bodyParams : new HashMap<>()))
                .retrieve();
    }


}
