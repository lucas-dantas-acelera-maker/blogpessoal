package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.service.PostagemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
