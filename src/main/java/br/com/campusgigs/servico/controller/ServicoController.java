package br.com.campusgigs.servico.controller;

import br.com.campusgigs.servico.dto.ServicoResponseDTO;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.service.ServicoService;
import br.com.campusgigs.usuario.model.Usuario;
import br.com.campusgigs.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;
    private final UsuarioRepository usuarioRepository;

    public ServicoController(ServicoService servicoService, UsuarioRepository usuarioRepository) {
        this.servicoService = servicoService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ServicoResponseDTO> publicar(@RequestBody Servico servico, @RequestParam Long idPrestador) {

        Usuario prestador = usuarioRepository.findById(idPrestador)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ServicoResponseDTO> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(
                ServicoResponseDTO.fromEntity(servicoService.encerrar(id))
        );
    }
}