package br.com.campusgigs.security.controller;

import br.com.campusgigs.security.dto.AuthenticationDTO;
import br.com.campusgigs.security.dto.LoginResponseDTO;
import br.com.campusgigs.security.dto.RegisterDTO;
import br.com.campusgigs.security.infra.TokenService;
import br.com.campusgigs.security.service.AuthenticationService;
import br.com.campusgigs.usuario.dto.UsuarioResponseDTO;
import br.com.campusgigs.usuario.dto.UsuarioRequestDTO;
import br.com.campusgigs.usuario.model.Usuario;
import br.com.campusgigs.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, UsuarioRepository userRepository, TokenService tokenService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
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
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (this.userRepository.findByEmail(registerDTO.email()) != null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(authenticationService.registrarUsuario(registerDTO));
    }

    @PutMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.atualizarUsuario(id, request));
    }
}