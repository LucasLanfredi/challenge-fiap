package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long idRestaurante;

    private final String nome;

    private final Endereco endereco;

    private final String tipoDeCozinha;

    private final HorariosDeFuncionamento horarioDeFuncionamento;

    private final Usuario DonoDoRestaurante;
}
