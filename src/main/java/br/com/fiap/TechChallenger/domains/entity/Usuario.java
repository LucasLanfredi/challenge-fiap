package br.com.fiap.TechChallenger.domains.entity;

import br.com.fiap.TechChallenger.domains.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}