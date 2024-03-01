package br.com.redemobconsorcio.rocket.repository;

import br.com.redemobconsorcio.rocket.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = ?1")
    Usuario findByLogin(String login);

    @Transactional
    @Modifying
    @Query(value = "insert into usuarios_role (usuario_id, role_id) values (?1, (select id from role where name = 'ROLE_CANDIDATO'));", nativeQuery = true)
    void inserirRoleCandidato(Long idUsuario);

    @Query(value = "SELECT constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user'", nativeQuery = true)
    String consultarConstraintRole();

}
