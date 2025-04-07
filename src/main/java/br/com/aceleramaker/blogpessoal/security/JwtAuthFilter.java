package br.com.aceleramaker.blogpessoal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtSecurity jwtSecurity;
    private final UsuarioSecurityService usuarioSecurityService;

    public JwtAuthFilter(JwtSecurity jwtSecurity, UsuarioSecurityService usuarioSecurityService) {
        this.jwtSecurity = jwtSecurity;
        this.usuarioSecurityService = usuarioSecurityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String usuario = jwtSecurity.extrairUsuario(token);

            if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = usuarioSecurityService.loadUserByUsername(usuario);

                if (jwtSecurity.validarToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
