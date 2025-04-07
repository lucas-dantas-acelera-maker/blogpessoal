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

    public PostagemDTO() {
    }

    public PostagemDTO(String titulo, String texto, Long usuarioId) {
        this.titulo = titulo;
        this.texto = texto;
        this.usuarioId = usuarioId;
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

    public Long getUsuario_id() {
        return usuarioId;
    }

    public void setUsuario_id(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
