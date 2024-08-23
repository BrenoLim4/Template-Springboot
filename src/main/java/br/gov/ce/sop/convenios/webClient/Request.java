package br.gov.ce.sop.convenios.webClient;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

@Builder
public record Request(@NotEmpty HttpMethod method, String baseUrl, @NotEmpty String endPoint, Map<String, Object> bodyRequest, Object bodyRequestObject, Map<String, List<String>> requestParams, String... pathVariables) {

}
