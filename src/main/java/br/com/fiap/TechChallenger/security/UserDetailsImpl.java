package br.com.fiap.TechChallenger.security;

import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.model.TipoUsuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private Long id;
    private final String username;
    @Getter
    private String email;
    private final String senha;
    @Getter
    private TipoUsuario tipoUsuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String senha,
                           TipoUsuario tipoUsuario,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuario usuario) {
        String role = usuario.getTipoUsuario().equals(TipoUsuario.DONO_RESTAURANTE)
                ? "ROLE_RESTAURANTE"
                : "ROLE_USER";
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipoUsuario(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }
}

