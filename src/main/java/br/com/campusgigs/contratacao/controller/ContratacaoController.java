package br.com.campusgigs.contratacao.controller;

import br.com.campusgigs.contratacao.dto.ContratacaoResponseDTO;
import br.com.campusgigs.contratacao.model.Contratacao;
import br.com.campusgigs.contratacao.service.ContratacaoService;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contratacoes")
public class ContratacaoController {

    private final ContratacaoService contratacaoService;

    public ContratacaoController(ContratacaoService contratacaoService) {
        this.contratacaoService = contratacaoService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ContratacaoResponseDTO> contratar(@RequestParam Long idServico, @AuthenticationPrincipal Usuario contratante) {

        Contratacao contratacao = contratacaoService.contratar(idServico, contratante);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContratacaoResponseDTO.fromEntity(contratacao));
    }
}
