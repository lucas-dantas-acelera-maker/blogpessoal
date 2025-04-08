package br.com.aceleramaker.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "postagens")
@Schema(description = "Representa uma postagem no blog")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da postagem", example = "1")
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Schema(description = "Título da postagem", example = "Spring Boot com JWT")
    private String titulo;

    @Column(nullable = false)
    @NotBlank
    @Schema(description = "Conteúdo da postagem", example = "Neste post vamos aprender como implementar autenticação JWT com Spring Boot...")
    private String texto;

    @Column(nullable = false)
    @Schema(description = "Data e hora em que a postagem foi criada", example = "2025-04-07T10:15:30")
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "tema_id")
    @JsonIgnoreProperties("postagens")
    @Schema(description = "Tema associado à postagem")
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("postagens")
    @Schema(description = "Usuário autor da postagem")
    private Usuario usuario;

    public Postagem(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
        this.data = LocalDateTime.now();
    }

    public Postagem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
