package br.com.redemobconsorcio.rocket.service;

import br.com.redemobconsorcio.rocket.entity.Usuario;
import br.com.redemobconsorcio.rocket.repository.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.redemobconsorcio.rocket.util.Constantes.SQL_DROP_CONSTRAINT_USUARIO_ROLE;
import static java.util.Objects.nonNull;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final JdbcTemplate jdbcTemplate;

    public UsuarioService(UsuarioRepository usuarioRepository, JdbcTemplate jdbcTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void salvarUsuario(Usuario usuario) {
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        String constraint = usuarioRepository.consultarConstraintRole();
        if (nonNull(constraint)) {
            jdbcTemplate.execute(SQL_DROP_CONSTRAINT_USUARIO_ROLE + constraint);
        }
        usuarioRepository.inserirRoleCandidato(usuarioSalvo.getId());
    }

    public String getCpfUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

}
