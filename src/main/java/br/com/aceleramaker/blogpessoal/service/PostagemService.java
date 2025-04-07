package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.PostagemRepository;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PostagemService {
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public PostagemService(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;

    }

    public Postagem salvarPostagem(PostagemDTO postagemDTO) {
        Usuario autor = buscarUsuarioAutorPorId(postagemDTO.getUsuario_id());

        Postagem postagem = new Postagem();
        postagem.setTitulo(postagemDTO.getTitulo());
        postagem.setTexto(postagemDTO.getTexto());
        postagem.setData(LocalDateTime.now());
        postagem.setUsuario(autor);

        autor.adicionarPostagem(postagem);

        return postagemRepository.save(postagem);
    }

    public Usuario buscarUsuarioAutorPorId(Long usuario_id) {
        return usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado pelo ID fornecido"));
    }
}
