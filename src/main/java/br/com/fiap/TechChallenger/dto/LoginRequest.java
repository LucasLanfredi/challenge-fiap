package br.com.fiap.TechChallenger.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String senha;
}
