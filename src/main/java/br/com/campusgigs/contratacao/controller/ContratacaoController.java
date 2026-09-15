package br.com.campusgigs.contratacao.controller;

import br.com.campusgigs.contratacao.model.Contratacao;
import br.com.campusgigs.contratacao.service.ContratacaoService;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: adicionar controle de acesso com JWT
@RestController
@RequestMapping("/contratacoes")
public class ContratacaoController {

    private final ContratacaoService contratacaoService;

    public ContratacaoController(ContratacaoService contratacaoService) {
        this.contratacaoService = contratacaoService;
    }

    @PostMapping
    public ResponseEntity<Contratacao> contratar(@RequestParam Long idServico, @RequestParam Long idContratante) {

        Servico servico = new Servico();
        servico.setId(idServico);

        Usuario contratante = new Usuario();
        contratante.setId(idContratante);

        Contratacao contratacao = contratacaoService.contratar(servico, contratante);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contratacao);
    }
}