package br.com.aceleramaker.blogpessoal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para criação e atualização de postagens")
public class PostagemDTO {

    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título da postagem", example = "Dicas de Spring Boot")
    private String titulo;

    @NotBlank(message = "Texto é obrigatório")
    @Schema(description = "Conteúdo da postagem", example = "Hoje vamos aprender como criar APIs REST com Spring Boot...")
    private String texto;

    @NotNull(message = "ID do usuário é obrigatório")
    @Schema(description = "ID do usuário autor da postagem", example = "1")
    private Long usuarioId;

    @NotNull(message = "ID do tema é obrigatório")
    @Schema(description = "ID do tema associado à postagem", example = "2")
    private Long temaId;

    public PostagemDTO() {
    }

    public PostagemDTO(String titulo, String texto, Long usuarioId, Long temaId) {
        this.titulo = titulo;
        this.texto = texto;
        this.usuarioId = usuarioId;
        this.temaId = temaId;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }
}
