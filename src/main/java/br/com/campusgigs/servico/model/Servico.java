package br.com.campusgigs.servico.model;

import br.com.campusgigs.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cg_servico")
@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_prestador")
    private Usuario prestador;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "preco")
    private Double preco;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private Situacao situacao;

    public Usuario getPrestador() {
        return prestador;
    }

    public void setPrestador(Usuario prestador) {
        this.prestador = prestador;
    }

}
