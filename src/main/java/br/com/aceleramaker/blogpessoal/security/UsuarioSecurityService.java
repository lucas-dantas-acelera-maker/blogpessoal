package br.com.aceleramaker.blogpessoal.security;

import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;

@Service
public class UsuarioSecurityService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return new User(usuarioEncontrado.getUsuario(), usuarioEncontrado.getSenha(), new ArrayList<>());
    }
}
