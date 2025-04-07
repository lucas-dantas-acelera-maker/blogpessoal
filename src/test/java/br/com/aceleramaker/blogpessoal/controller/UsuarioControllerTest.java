package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.UsuarioLoginDTO;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.security.JwtSecurity;
import br.com.aceleramaker.blogpessoal.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class UsuarioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtSecurity jwtSecurity;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getUrl(String path) {
        return "http://localhost:" + port + "/api/usuarios" + path;
    }

    @Test
    void deveCriarUsuario() {
        Usuario usuario = new Usuario(null, "novoUsuario", "senha123", "email@email.com");

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
                getUrl(""),
                usuario,
                Usuario.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsuario()).isEqualTo("novoUsuario");
    }

    @Test
    void deveAutenticarUsuarioERetornarToken() {
        Usuario usuario = new Usuario(null, "loginuser", "senha123", "email@teste.com");
        usuarioService.salvarUsuario(usuario); // salva com senha criptografada

        UsuarioLoginDTO login = new UsuarioLoginDTO("loginuser", "senha123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UsuarioLoginDTO> request = new HttpEntity<>(login, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                getUrl("/login"),
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Bearer ");
    }

    @Test
    void deveAlterarUsuario() {
        Usuario usuario = new Usuario(null, "updateuser", "senha123", "update@email.com");
        Usuario salvo = usuarioService.salvarUsuario(usuario);

        salvo.setUsuario("usuarioAtualizado");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Usuario> request = new HttpEntity<>(salvo, headers);

        ResponseEntity<Usuario> response = restTemplate.exchange(
                getUrl("/" + salvo.getId()),
                HttpMethod.PUT,
                request,
                Usuario.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsuario()).isEqualTo("usuarioAtualizado");
    }

    @Test
    void deveDeletarUsuario() {
        Usuario usuario = new Usuario(null, "deleteuser", "senha123", "delete@email.com");
        Usuario salvo = usuarioService.salvarUsuario(usuario);

        ResponseEntity<Void> response = restTemplate.exchange(
                getUrl("/" + salvo.getId()),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .sessionManagement(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }

    @TestConfiguration
    static class TestBeans {

        @Bean
        public JwtSecurity jwtSecurity() {
            return mock(JwtSecurity.class);
        }
    }
}
