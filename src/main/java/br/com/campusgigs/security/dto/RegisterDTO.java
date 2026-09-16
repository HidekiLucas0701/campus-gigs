package br.com.campusgigs.security.dto;

import br.com.campusgigs.usuario.model.Papel;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterDTO(
        @NotBlank(message = "O nome de usuário não pode ser nulo ou vazio")
        @Size(max = 100, message = "O nome de usuário deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O e-mail não pode ser nulo ou vazio")
        @Email(message = "Formato de e-mail inválido")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "A senha não pode ser nula ou vazia")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
        String cep,

        @NotNull(message = "O tipo de usuário não pode ser nulo")
        Papel papel
) {
}