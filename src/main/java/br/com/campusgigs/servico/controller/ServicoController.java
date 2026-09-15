package br.com.campusgigs.servico.controller;

import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.service.ServicoService;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: adicionar controle de acesso com JWT
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<Servico> publicar(@RequestBody Servico servico, @RequestParam Long idPrestador) {

        Usuario prestador = new Usuario();
        prestador.setId(idPrestador);

        Servico servicoPublicado =
                servicoService.publicar(servico, prestador);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicoPublicado);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(servicoService.listar());
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<Servico> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(
                servicoService.encerrar(id)
        );
    }
}