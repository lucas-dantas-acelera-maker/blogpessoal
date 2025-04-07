package br.com.aceleramaker.blogpessoal.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDTO {
    @NotBlank(message = "Nome de usuário é obrigatório")
    private String usuario;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    public UsuarioLoginDTO() {
    }

    public UsuarioLoginDTO(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
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
}
