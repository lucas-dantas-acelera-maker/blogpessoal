package br.com.aceleramaker.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "temas")
@Schema(description = "Representa um tema utilizado nas postagens do blog")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do tema", example = "1")
    private Long id;

    @Column
    @NotBlank(message = "Nome/descricao do tema é obrigatória")
    @Schema(description = "Nome ou descrição do tema", example = "Tecnologia")
    private String descricao;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tema")
    @Schema(description = "Lista de postagens associadas ao tema")
    private final List<Postagem> postagens = new ArrayList<>();

    public Tema(String descricao) {
        this.descricao = descricao;
    }

    public Tema() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
