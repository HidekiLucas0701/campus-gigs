package br.com.campusgigs.usuario.dto;

import br.com.campusgigs.servico.dto.ServicoResponseDTO;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.usuario.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
