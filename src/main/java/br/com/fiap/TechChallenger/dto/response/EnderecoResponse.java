package br.com.fiap.TechChallenger.dto.response;

import br.com.fiap.TechChallenger.entity.Endereco;

public record EnderecoResponse(
        Long id,
        String rua,
        String numero,
        String cidade,
        String estado,
        String cep
) {
    public static EnderecoResponse fromEntity(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }
}
