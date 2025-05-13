package br.com.fiap.TechChallenger.domains.dto;

import lombok.Data;

@Data
public class TrocaSenhaDto {
    private String login;
    private String senhaAtual;
    private String NovaSenha;
}
