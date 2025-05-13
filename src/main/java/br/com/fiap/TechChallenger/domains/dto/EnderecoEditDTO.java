package br.com.fiap.TechChallenger.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEditDTO {

    private Long id;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

}

