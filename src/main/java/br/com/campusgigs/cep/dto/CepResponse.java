package br.com.campusgigs.cep.dto;

public record CepResponse(
        String cep,
        String localidade,
        String uf,
        Boolean erro
) {
}