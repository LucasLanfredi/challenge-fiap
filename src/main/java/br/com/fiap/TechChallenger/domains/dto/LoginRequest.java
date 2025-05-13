package br.com.fiap.TechChallenger.domains.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String senha;
}
