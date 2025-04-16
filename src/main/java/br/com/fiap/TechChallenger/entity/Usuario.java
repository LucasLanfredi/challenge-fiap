package br.com.fiap.TechChallenger.entity;

import br.com.fiap.TechChallenger.model.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    private String login;

    private String senha;

    private LocalDateTime dataUltimaAlteracao;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}