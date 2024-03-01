package br.com.redemobconsorcio.rocket.security;

import br.com.redemobconsorcio.rocket.entity.Usuario;
import br.com.redemobconsorcio.rocket.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public ImplementacaoUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username);

        if (isNull(usuario)) {
            throw new UsernameNotFoundException("Usuário não foi encontrado!");
        }

        return new User(usuario.getLogin(), usuario.getPassword(),
            usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
    }

}
