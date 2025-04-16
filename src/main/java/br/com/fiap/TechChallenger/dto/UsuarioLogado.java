package br.com.fiap.TechChallenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogado {

    private String nome;
    private String email;
    private Long id;

}
