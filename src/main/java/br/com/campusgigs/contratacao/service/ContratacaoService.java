package br.com.campusgigs.contratacao.service;

import br.com.campusgigs.contratacao.model.Contratacao;
import br.com.campusgigs.contratacao.model.SituacaoContratacao;
import br.com.campusgigs.contratacao.repository.ContratacaoRepository;
import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.model.SituacaoServico;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class ContratacaoService {

    private final ContratacaoRepository contratacaoRepository;

    public ContratacaoService(ContratacaoRepository contratacaoRepository) {
        this.contratacaoRepository = contratacaoRepository;
    }

    public Contratacao contratar(Servico servico, Usuario contratante) {

        if (servico.getSituacao() != SituacaoServico.ATIVO) {
            throw new RuntimeException("O serviço não está disponível para contratação");
        }

        Contratacao contratacao = new Contratacao();

        contratacao.setServico(servico);
        contratacao.setContratante(contratante);
        contratacao.setSituacao(SituacaoContratacao.SOLICITADA);

        return contratacaoRepository.save(contratacao);
    }
}