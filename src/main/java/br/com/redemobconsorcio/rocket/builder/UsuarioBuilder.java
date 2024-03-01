package br.com.redemobconsorcio.rocket.builder;

import br.com.redemobconsorcio.rocket.entity.Role;
import br.com.redemobconsorcio.rocket.entity.Usuario;

import java.util.List;

public class UsuarioBuilder {

    private Long id;
    private String login;
    private String senha;
    private List<Role> roles;

    private UsuarioBuilder() {
    }

    public static UsuarioBuilder newBuilder() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UsuarioBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UsuarioBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    public UsuarioBuilder roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Usuario build() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setRoles(roles);
        return usuario;
    }
}
