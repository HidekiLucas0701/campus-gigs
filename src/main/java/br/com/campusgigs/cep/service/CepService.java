package br.com.campusgigs.cep.service;

import br.com.campusgigs.cep.dto.CepResponse;
import br.com.campusgigs.cep.exception.CepNaoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ViaCepService viaCepService;

    public CepService(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    public CepResponse buscarCep(String cep) {
        String cepNormalizado = cep.replaceAll("\\D", "");
        CepResponse response = viaCepService.getCep(cepNormalizado);

        if (response == null || Boolean.TRUE.equals(response.erro())) {
            throw new CepNaoEncontradoException(cep);
        }

        return response;
    }
}