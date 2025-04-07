package br.com.aceleramaker.blogpessoal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostagemDTO {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Texto é obrigatório")
    private String texto;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID do tema é obrigatório")
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
