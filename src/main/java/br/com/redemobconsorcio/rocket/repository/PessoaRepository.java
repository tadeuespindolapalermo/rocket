package br.com.redemobconsorcio.rocket.repository;

import br.com.redemobconsorcio.rocket.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByCpf(String cpf);

}
