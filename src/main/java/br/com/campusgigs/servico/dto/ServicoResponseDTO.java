package br.com.campusgigs.servico.dto;

import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.model.SituacaoServico;
import br.com.campusgigs.usuario.dto.UsuarioResponseDTO;

public record ServicoResponseDTO(
        Long id,
        UsuarioResponseDTO prestador,
        String titulo,
        String descricao,
        String categoria,
        Double preco,
        SituacaoServico situacao
) {
    public static ServicoResponseDTO fromEntity(Servico servico){
        return new ServicoResponseDTO(
                servico.getId(),
                UsuarioResponseDTO.fromEntity(servico.getPrestador()),
                servico.getTitulo(),
                servico.getDescricao(),
                servico.getCategoria(),
                servico.getPreco(),
                servico.getSituacao()
        );
    }
}
