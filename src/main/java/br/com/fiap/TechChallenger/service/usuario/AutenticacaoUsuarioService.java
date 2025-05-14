package br.com.fiap.TechChallenger.service.usuario;

import br.com.fiap.TechChallenger.dto.response.JwtResponse;
import br.com.fiap.TechChallenger.dto.LoginRequest;
import br.com.fiap.TechChallenger.security.JwtUtils;
import br.com.fiap.TechChallenger.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoUsuarioService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> autenticar(LoginRequest loginRequest) {

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

}