package br.com.aceleramaker.blogpessoal.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "temas")
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
    private final List<Postagem> postagens = new ArrayList<>();

    public Tema(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void adicionarPostagem(Postagem postagem) {
        postagens.add(postagem);
        postagem.setTema(this);
    }
}
