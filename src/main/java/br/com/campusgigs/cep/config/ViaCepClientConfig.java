package br.com.campusgigs.cep.config;

import br.com.campusgigs.cep.service.ViaCepService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ViaCepClientConfig {

    @Bean
    ViaCepService viaCepService() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(ViaCepService.class);
    }
}