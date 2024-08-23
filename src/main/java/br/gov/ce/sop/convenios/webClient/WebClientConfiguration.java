package br.gov.ce.sop.convenios.webClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${base-url-api.controle-documento-digital}")
    private String baseUrlDefault;//base url padrão, pode ser substituída ao construir à requisição.
    @Bean
    public WebClient webClient(WebClient.Builder builder){

        return  builder
                .baseUrl(baseUrlDefault)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(
                        //configuração para aumentar o limite do buffer do JSON
                        ExchangeStrategies.builder()
                                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(150000000)).build())
                .build();
    }

}
