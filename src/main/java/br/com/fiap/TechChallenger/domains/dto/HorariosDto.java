package br.com.fiap.TechChallenger.domains.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HorariosDto {
    private String abertura;
    private String fechamento;
}
