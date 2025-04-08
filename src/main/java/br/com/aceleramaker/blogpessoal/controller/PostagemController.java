package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.service.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postagens")
@Tag(name = "Postagens", description = "API para gerenciamento de postagens")
public class PostagemController {
    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @PostMapping
    @Operation(summary = "Criar nova postagem",
            description = "Cria uma nova postagem no blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Postagem criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Postagem.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido")
    })
    public ResponseEntity<Postagem> criarPostagem(@Valid @RequestBody PostagemDTO postagemDTO) {
        Postagem novaPostagem = postagemService.salvarPostagem(postagemDTO);
        return new ResponseEntity<>(novaPostagem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar postagem existente",
            description = "Atualiza os dados de uma postagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Postagem atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Postagem.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido")
    })
    public ResponseEntity<Postagem> alterarPostagem(@Valid @PathVariable Long id, @RequestBody PostagemDTO postagemDTO) {
        Postagem postagemAlterada = postagemService.alterarPostagem(id, postagemDTO);
        return new ResponseEntity<>(postagemAlterada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir postagem",
            description = "Remove uma postagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Postagem excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido")
    })
    public ResponseEntity<Void> deletarPostagem(@PathVariable Long id) {
        postagemService.deletarPostagem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Listar todas as postagens",
            description = "Retorna todas as postagens cadastradas no blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de postagens recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Postagem.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido")
    })
    public ResponseEntity<List<Postagem>> buscarPostagens() {
        List<Postagem> postagens = postagemService.buscarPostagens();
        return new ResponseEntity<>(postagens, HttpStatus.OK);
    }

    @GetMapping("/filtro")
    @Operation(summary = "Buscar postagens com filtro",
            description = "Retorna postagens filtradas por autor (usuário) e/ou tema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de postagens filtradas recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Postagem.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido")
    })
    public ResponseEntity<List<Postagem>> buscarPostagensComFiltro(
            @RequestParam(required = false, name = "autor") Long usuarioId,
            @RequestParam(required = false, name = "tema") Long temaId
    ) {
        List<Postagem> postagens = postagemService.buscarPostagensComFiltro(usuarioId, temaId);

        return new ResponseEntity<>(postagens, HttpStatus.OK);
    }
}
