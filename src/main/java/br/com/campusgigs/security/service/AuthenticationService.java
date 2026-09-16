package br.com.campusgigs.security.service;

import br.com.campusgigs.cep.dto.CepResponse;
import br.com.campusgigs.cep.service.CepService;
import br.com.campusgigs.security.dto.RegisterDTO;
import br.com.campusgigs.usuario.dto.UsuarioResponseDTO;
import br.com.campusgigs.usuario.dto.UsuarioRequestDTO;
import br.com.campusgigs.usuario.model.Usuario;
import br.com.campusgigs.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UsuarioRepository userRepository;
    private final CepService cepService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioRepository userRepository, CepService cepService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cepService = cepService;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO registrarUsuario(RegisterDTO registerDTO) {
        CepResponse cepResponse = cepService.buscarCep(registerDTO.cep());

        Usuario newUser = new Usuario(
                registerDTO.nome(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.senha()),
                registerDTO.papel()
        );
        newUser.setCep(cepResponse.cep());
        newUser.setCidade(cepResponse.localidade());
        newUser.setUf(cepResponse.uf());

        return UsuarioResponseDTO.fromEntity(userRepository.save(newUser));
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO request) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        if (!usuario.getCep().equals(request.cep())) {
            CepResponse cepResponse = cepService.buscarCep(request.cep());
            usuario.setCep(cepResponse.cep());
            usuario.setCidade(cepResponse.localidade());
            usuario.setUf(cepResponse.uf());
        }

        return UsuarioResponseDTO.fromEntity(userRepository.save(usuario));
    }
}
