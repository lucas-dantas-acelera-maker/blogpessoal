package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.service.TemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
@Tag(name = "Temas", description = "API para gerenciamento de temas de postagens")
public class TemaController {
    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @PostMapping
    @Operation(summary = "Criar novo tema",
            description = "Cria um novo tema no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tema criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o tema")
    })
    public ResponseEntity<Tema> criarTema(@Valid @RequestBody Tema tema) {
        Tema novoTema = temaService.salvarTema(tema);
        return new ResponseEntity<>(novoTema, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tema existente",
            description = "Atualiza um tema existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o tema"),
            @ApiResponse(responseCode = "404", description = "Tema não encontrado")
    })
    public ResponseEntity<Tema> alterarTema(@Valid @PathVariable Long id, @RequestBody Tema tema) {
        tema.setId(id);
        Tema temaAlterado = temaService.alterarTema(tema);
        return new ResponseEntity<>(temaAlterado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tema",
            description = "Remove um tema específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tema excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tema não encontrado")
    })
    public ResponseEntity<Void> deletarTema(@PathVariable Long id) {
        temaService.deletarTema(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Listar temas",
            description = "Retorna a lista de todos os temas cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temas retornados com sucesso")
    })
    public ResponseEntity<List<Tema>> buscarTemas() {
        List<Tema> temas = temaService.buscarTemas();
        return new ResponseEntity<>(temas, HttpStatus.OK);
    }
}
