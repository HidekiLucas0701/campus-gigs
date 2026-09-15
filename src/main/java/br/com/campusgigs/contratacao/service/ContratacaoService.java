package br.com.campusgigs.contratacao.service;

import br.com.campusgigs.contratacao.model.Contratacao;
import br.com.campusgigs.contratacao.model.SituacaoContratacao;
import br.com.campusgigs.contratacao.repository.ContratacaoRepository;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.model.SituacaoServico;
import br.com.campusgigs.servico.repository.ServicoRepository;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContratacaoService {

    private final ContratacaoRepository contratacaoRepository;
    private final ServicoRepository servicoRepository;

    public ContratacaoService(ContratacaoRepository contratacaoRepository, ServicoRepository servicoRepository) {
        this.contratacaoRepository = contratacaoRepository;
        this.servicoRepository = servicoRepository;
    }

    public Contratacao contratar(Long idServico, Usuario contratante) {
        Servico servico = servicoRepository.findById(idServico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        if (servico.getSituacao() != SituacaoServico.ATIVO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O serviço não está disponível para contratação");
        }

        if (servico.getPrestador().getId().equals(contratante.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode contratar o próprio serviço");
        }

        Contratacao contratacao = new Contratacao();

        contratacao.setServico(servico);
        contratacao.setContratante(contratante);
        contratacao.setSituacao(SituacaoContratacao.SOLICITADA);

        return contratacaoRepository.save(contratacao);
    }
}
