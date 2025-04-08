package br.com.aceleramaker.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Schema(description = "Representa um usuário da aplicação")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do usuário", example = "1")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do usuário", example = "Maria Silva")
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "Nome de usuário é obrigatório")
    @Schema(description = "Nome de usuário utilizado para login", example = "maria123")
    private String usuario;

    @Column(nullable = false)
    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha de acesso do usuário", example = "12345678", minLength = 8)
    private String senha;

    @Column()
    @Schema(description = "URL da foto de perfil do usuário", example = "https://avatars.githubusercontent.com/u/130024434?v=4")
    private String foto = "https://avatars.githubusercontent.com/u/130024434?v=4";

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("usuario")
    @Schema(description = "Lista de postagens do usuário (relacionamento com a entidade Postagem)")
    private final List<Postagem> postagens = new ArrayList<>();

    public Usuario(String nome, String usuario, String senha, String foto) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void adicionarPostagem(Postagem postagem) {
        this.postagens.add(postagem);
        postagem.setUsuario(this);
    }
}
