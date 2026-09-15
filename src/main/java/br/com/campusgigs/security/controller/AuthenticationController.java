package br.com.campusgigs.security.controller;

import br.com.campusgigs.security.dto.AuthenticationDTO;
import br.com.campusgigs.security.dto.LoginResponseDTO;
import br.com.campusgigs.security.dto.RegisterDTO;
import br.com.campusgigs.security.infra.TokenService;
import br.com.campusgigs.usuario.model.Usuario;
import br.com.campusgigs.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UsuarioRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (this.userRepository.findByEmail(registerDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.senha());
        Usuario newUser = new Usuario(registerDTO.nome(), registerDTO.email(), encryptedPassword, /**registerDTO.endereco(),**/ registerDTO.papel());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}