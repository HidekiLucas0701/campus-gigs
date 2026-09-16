package br.com.campusgigs.servico.service;

import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.model.SituacaoServico;
import br.com.campusgigs.servico.repository.ServicoRepository;
import br.com.campusgigs.usuario.model.Papel;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico publicar(Servico servico, Usuario prestador) {
        servico.setPrestador(prestador);
        servico.setSituacao(SituacaoServico.ATIVO);

        return servicoRepository.save(servico);
    }

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public Servico encerrar(Long id, Usuario usuarioLogado) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        boolean isDono = servico.getPrestador().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getPapel() == Papel.ADMIN;

        if (!isDono && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode encerrar os seus próprios serviços");
        }

        servico.setSituacao(SituacaoServico.ENCERRADO);

        return servicoRepository.save(servico);
    }
}