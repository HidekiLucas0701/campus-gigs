package br.com.campusgigs.servico.controller;

import br.com.campusgigs.servico.dto.ServicoResponseDTO;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.service.ServicoService;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ServicoResponseDTO> publicar(@RequestBody Servico servico, @AuthenticationPrincipal Usuario prestador) {

        Servico servicoPublicado =
                servicoService.publicar(servico, prestador);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ServicoResponseDTO.fromEntity(servicoPublicado));
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar() {
        return ResponseEntity.ok(
                servicoService.listar().stream()
                        .map(ServicoResponseDTO::fromEntity)
                        .toList()
        );
    }

    @PutMapping("/{id}/encerrar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ServicoResponseDTO> encerrar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(
                ServicoResponseDTO.fromEntity(servicoService.encerrar(id, usuarioLogado))
        );
    }
}