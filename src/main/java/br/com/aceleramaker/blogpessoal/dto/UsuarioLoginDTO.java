package br.com.aceleramaker.blogpessoal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para autenticação de usuário (login)")
public class UsuarioLoginDTO {

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Schema(description = "Nome de usuário utilizado para login", example = "joaosilva")
    private String usuario;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "123456")
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
