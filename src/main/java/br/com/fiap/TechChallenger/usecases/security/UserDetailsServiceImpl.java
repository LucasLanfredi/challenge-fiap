package br.com.fiap.TechChallenger.usecases.security;

import br.com.fiap.TechChallenger.domains.UserDetailsImpl;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com login: " + username));
        return UserDetailsImpl.build(usuario);
    }
}
