package br.com.campusgigs.servico.service;

import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.servico.model.SituacaoServico;
import br.com.campusgigs.servico.repository.ServicoRepository;
import br.com.campusgigs.usuario.model.Usuario;
import org.springframework.stereotype.Service;

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

    public Servico encerrar(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servico.setSituacao(SituacaoServico.ENCERRADO);

        return servicoRepository.save(servico);
    }
}