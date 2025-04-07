package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.service.PostagemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postagens")
public class PostagemController {
    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @PostMapping
    public ResponseEntity<Postagem> criarPostagem(@Valid @RequestBody PostagemDTO postagemDTO) {
        Postagem novaPostagem = postagemService.salvarPostagem(postagemDTO);
        return new ResponseEntity<>(novaPostagem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> alterarPostagem(@Valid @PathVariable Long id, @RequestBody PostagemDTO postagemDTO) {
        Postagem postagemAlterada = postagemService.alterarPostagem(id, postagemDTO);
        return new ResponseEntity<>(postagemAlterada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPostagem(@PathVariable Long id) {
        postagemService.deletarPostagem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Postagem>> buscarPostagens() {
        List<Postagem> postagens = postagemService.buscarPostagens();
        return new ResponseEntity<>(postagens, HttpStatus.OK);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Postagem>> buscarPostagensComFiltro(
            @RequestParam(required = false, name = "autor") Long usuarioId,
            @RequestParam(required = false, name = "tema") Long temaId
    ) {
        List<Postagem> postagens = postagemService.buscarPostagensComFiltro(usuarioId, temaId);

        return new ResponseEntity<>(postagens, HttpStatus.OK);
    }
}
