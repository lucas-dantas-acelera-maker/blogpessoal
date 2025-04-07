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
import java.util.ArrayList;
import java.util.List;

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

    public Postagem alterarPostagem(Long postId, PostagemDTO postagemDTO) {
        Postagem postagemAlterada = buscarPostagemPorId(postId);
        Usuario novoUsuario = buscarUsuarioAutorPorId(postagemDTO.getUsuarioId());
        Tema novoTema = buscarTemaPostagemPorId(postagemDTO.getTemaId());

        postagemAlterada.setTitulo(postagemDTO.getTitulo());
        postagemAlterada.setTexto(postagemDTO.getTexto());
        postagemAlterada.setData(LocalDateTime.now());
        postagemAlterada.setUsuario(novoUsuario);
        postagemAlterada.setTema(novoTema);

        return postagemRepository.save(postagemAlterada);
    }

    public void deletarPostagem(Long postId) {
        if (!postagemRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada pelo ID fornecido");
        }

        postagemRepository.deleteById(postId);
    }

    public List<Postagem> buscarPostagens() {
        Iterable<Postagem> postagensIterable = postagemRepository.findAll();
        List<Postagem> postagens = new ArrayList<>();
        postagensIterable.forEach(postagens::add);
        return postagens;
    }

    public List<Postagem> buscarPostagensComFiltro(Long usuarioId, Long temaId) {
        List<Postagem> postagens = buscarPostagens();

        return postagens.stream()
                .filter(post -> (usuarioId == null || post.getUsuario().getId().equals(usuarioId))
                        && (temaId == null || post.getTema().getId().equals(temaId)))
                .toList();
    }

    public Postagem buscarPostagemPorId(Long postId) {
        return postagemRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada pelo ID fornecido"));
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
