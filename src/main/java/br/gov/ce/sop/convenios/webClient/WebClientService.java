package br.gov.ce.sop.convenios.webClient;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

public interface WebClientService {
    WebClient.ResponseSpec api(HttpMethod method, String baseUrl , String endpoint, Map<String, ?> bodyParams, Map<String, List<String>> queryParams, String... pathVariables);

}
