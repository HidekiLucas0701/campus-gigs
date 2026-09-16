package br.com.campusgigs.usuario.dto;

import br.com.campusgigs.usuario.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String cep,
        String cidade,
        String uf
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCep(),
                usuario.getCidade(),
                usuario.getUf()
        );
    }
}
