package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;

public class EnderecoHelper {

    public static Endereco gerarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setEstado("SP");
        endereco.setRua("Rua numero 0");
        endereco.setCidade("Bairro do Bairro");
        endereco.setNumero("123");
        endereco.setCep("00000000");
        return endereco;
    }

    public static Endereco gerarEndereco(Usuario usuario ) {
        return new Endereco(
                1L,
                "São Paulo",
                "123",
                "SP",
                "123",
                "0000-000",
                usuario);
    }

    public static EnderecoDTO gerarEnderecoDTO() {
        return EnderecoDTO.builder()
                .estado("SP")
                .cidade("Bairro do Bairro")
                .rua("Rua numero 0")
                .numero("123")
                .cep("00000000")
                .build();
    }

    public static EnderecoEditDTO gerarEnderecoEditDTO() {
        return EnderecoEditDTO.builder()
                .enderecoId(1L)
                .estado(null)
                .cidade(null)
                .rua(null)
                .numero(null)
                .build();
    }

    public static EnderecoEditDTO gerarEnderecoEditDTO(Endereco endereco) {
        return EnderecoEditDTO.builder()
                .enderecoId(endereco.getId())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .build();
    }

}
