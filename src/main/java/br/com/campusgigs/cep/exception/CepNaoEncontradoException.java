package br.com.campusgigs.cep.exception;

public class CepNaoEncontradoException extends RuntimeException {
    public CepNaoEncontradoException(String cep) {
        super("CEP não encontrado: " + cep);
    }
}