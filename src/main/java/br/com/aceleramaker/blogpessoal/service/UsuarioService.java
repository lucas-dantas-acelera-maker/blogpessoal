package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario alterarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do usuário não informado.");
        }

        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado pelo ID fornecido.");
        }

        return usuarioRepository.save(usuario);
    }
}
