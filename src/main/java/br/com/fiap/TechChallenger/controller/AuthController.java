package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.dto.JwtResponse;
import br.com.fiap.TechChallenger.dto.LoginRequest;
import br.com.fiap.TechChallenger.dto.MessageResponse;
import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.security.JwtUtils;
import br.com.fiap.TechChallenger.security.UserDetailsImpl;
import br.com.fiap.TechChallenger.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getTipoUsuario()));
    }

    @PostMapping("/criar")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioDTO signUpRequest) {
        if (usuarioService.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Login já existente!"));
        }
        if (usuarioService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já existente!"));
        }
        Usuario user = usuarioService.registrar(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}