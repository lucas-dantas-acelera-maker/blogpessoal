package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.UsuarioLoginDTO;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.security.JwtSecurity;
import br.com.aceleramaker.blogpessoal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
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
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterarUsuario(@Valid @PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAlterado = usuarioService.alterarUsuario(usuario);
        return new ResponseEntity<>(usuarioAlterado, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        var usuario = usuarioService.autenticarUsuario(usuarioLoginDTO);

        if (usuario != null) {
            var token = jwtSecurity.gerarTokenJwt(usuario.getUsuario());
            return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuário ou senha inválidos", HttpStatus.UNAUTHORIZED);
   }
}
