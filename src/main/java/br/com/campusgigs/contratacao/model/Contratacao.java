package br.com.campusgigs.contratacao.model;

import br.com.campusgigs.servico.model.Servico;
import br.com.campusgigs.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cg_contratacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contratacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "id_contratante")
    private Usuario contratante;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoContratacao situacao;
}