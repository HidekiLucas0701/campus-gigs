package br.com.campusgigs.cep.service;

import br.com.campusgigs.cep.dto.CepResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
        url = "https://viacep.com.br",
        accept = "application/json"
)
public interface ViaCepService {

    @GetExchange("/ws/{cep}/json/")
    CepResponse getCep(@PathVariable String cep);
}