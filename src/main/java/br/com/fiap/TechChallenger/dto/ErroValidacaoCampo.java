package br.com.fiap.TechChallenger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroValidacaoCampo {

    private String nomeCampo;
    private String mensagemErroValidacao;

}
