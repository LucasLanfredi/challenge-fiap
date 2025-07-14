package br.com.fiap.TechChallenger.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Horarios {
    private final String abertura;
    private final String fechamento;
}
