package br.com.fiap.TechChallenger.domains;

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

    @Setter
    private String nome;

    @Column(unique = true)
    @Email
    @Setter
    private String email;

    @Column(unique = true)
    private String login;

    private String senha;

    private Boolean admin;

    @Setter
    private LocalDateTime dataUltimaAlteracao;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "donoDoRestaurante")
    private List<Restaurante> restaurantes;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}