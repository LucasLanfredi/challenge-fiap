package br.com.fiap.TechChallenger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UsuarioLogado {

    private String nome;
    private String email;
    private Long id;
    private String perfil;

}
