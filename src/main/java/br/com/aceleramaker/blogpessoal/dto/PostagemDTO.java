package br.com.aceleramaker.blogpessoal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostagemDTO {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Texto é obrigatório")
    private String texto;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuario_id;

    public PostagemDTO() {
    }

    public PostagemDTO(String titulo, String texto, Long usuario_id) {
        this.titulo = titulo;
        this.texto = texto;
        this.usuario_id = usuario_id;
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
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }
}
