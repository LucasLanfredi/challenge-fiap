package br.com.fiap.TechChallenger.service.endereco;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Endereco;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarEnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<List<Endereco>> buscarEnderecos(HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacaoService.getUsuarioLogado(request);
            final Usuario usuarioEntity = usuarioRepository.getUsuarioById(usuariologado.getId());
            return ResponseEntity.ok(enderecoRepository.getEnderecosByUsuario(usuarioEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}