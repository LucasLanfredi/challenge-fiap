package br.com.fiap.TechChallenger.domains.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErroCustomizado {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private List<ErroValidacaoCampo> listaErrosValidacao = new ArrayList<>();

    public ErroCustomizado(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public void addErroValidacao(String nomeCampo, String mensagemErroValidacao){
        listaErrosValidacao.add(new ErroValidacaoCampo(nomeCampo, mensagemErroValidacao));
    }

}
