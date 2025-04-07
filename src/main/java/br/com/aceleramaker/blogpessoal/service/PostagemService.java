package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.PostagemRepository;
import br.com.aceleramaker.blogpessoal.repository.TemaRepository;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PostagemService {
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final TemaRepository temaRepository;

    public PostagemService(
            PostagemRepository postagemRepository,
            UsuarioRepository usuarioRepository,
            TemaRepository temaRepository
    ) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.temaRepository = temaRepository;

    }

    public Postagem salvarPostagem(PostagemDTO postagemDTO) {
        Usuario autor = buscarUsuarioAutorPorId(postagemDTO.getUsuarioId());
        Tema temaPostagem = buscarTemaPostagemPorId(postagemDTO.getTemaId());

        Postagem postagem = new Postagem();
        postagem.setTitulo(postagemDTO.getTitulo());
        postagem.setTexto(postagemDTO.getTexto());
        postagem.setData(LocalDateTime.now());
        postagem.setUsuario(autor);
        postagem.setTema(temaPostagem);

        autor.adicionarPostagem(postagem);
        temaPostagem.adicionarPostagem(postagem);

        return postagemRepository.save(postagem);
    }

    public Usuario buscarUsuarioAutorPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado pelo ID fornecido"));
    }

    public Tema buscarTemaPostagemPorId(Long temaId) {
        return temaRepository.findById(temaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado pelo ID fornecido"));
    }
}
