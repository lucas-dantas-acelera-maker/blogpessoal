package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.UsuarioLoginDTO;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.security.JwtSecurity;
import br.com.aceleramaker.blogpessoal.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authManager;
    private final JwtSecurity jwtSecurity;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authManager, JwtSecurity jwtSecurity) {
        this.usuarioService = usuarioService;
        this.authManager = authManager;
        this.jwtSecurity = jwtSecurity;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de usuário inválidos"),
            @ApiResponse(responseCode = "409", description = "Nome de usuário já existente")
    })
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
   @Operation(summary = "Atualizar usuário existente",
           description = "Atualiza os dados de um usuário específico pelo ID")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
           @ApiResponse(responseCode = "400", description = "Dados de usuário inválidos"),
           @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
           @ApiResponse(responseCode = "401", description = "Não autorizado"),
           @ApiResponse(responseCode = "403", description = "Acesso proibido")
   })
    public ResponseEntity<Usuario> alterarUsuario(@Valid @PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAlterado = usuarioService.alterarUsuario(usuario);
        return new ResponseEntity<>(usuarioAlterado, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Excluir usuário",
           description = "Remove um usuário específico pelo ID")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
           @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
           @ApiResponse(responseCode = "401", description = "Não autorizado"),
           @ApiResponse(responseCode = "403", description = "Acesso proibido")
   })
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PostMapping("/login")
   @Operation(summary = "Autenticar usuário",
           description = "Realiza login do usuário e retorna um token JWT")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200",
                   description = "Autenticação bem-sucedida",
                   content = @Content(mediaType = "text/plain",
                           schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))),
           @ApiResponse(responseCode = "401",
                   description = "Credenciais inválidas",
                   content = @Content(mediaType = "text/plain",
                           schema = @Schema(type = "string", example = "Usuário ou senha inválidos")))
   })
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        var usuario = usuarioService.autenticarUsuario(usuarioLoginDTO);

        if (usuario != null) {
            var token = jwtSecurity.gerarTokenJwt(usuario.getUsuario());
            return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuário ou senha inválidos", HttpStatus.UNAUTHORIZED);
   }
}
