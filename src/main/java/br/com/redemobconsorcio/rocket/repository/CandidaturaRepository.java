package br.com.redemobconsorcio.rocket.repository;

import br.com.redemobconsorcio.rocket.entity.Candidatura;
import br.com.redemobconsorcio.rocket.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

    List<Candidatura> findByPessoaAndAprovacaoFalse(Pessoa pessoa);

    List<Candidatura> findByPessoaAndAprovacaoTrue(Pessoa pessoa);

    List<Candidatura> findByPessoa(Pessoa pessoa);

}
