package br.com.fiap.TechChallenger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
}

