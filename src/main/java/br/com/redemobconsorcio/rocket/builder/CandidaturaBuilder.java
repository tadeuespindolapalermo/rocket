package br.com.redemobconsorcio.rocket.builder;

import br.com.redemobconsorcio.rocket.entity.Candidatura;
import br.com.redemobconsorcio.rocket.entity.Pessoa;

public class CandidaturaBuilder {

    private Long id;
    private Boolean aprovacao;
    private Pessoa pessoa;

    private CandidaturaBuilder() {
    }

    public static CandidaturaBuilder newBuilder() {
        return new CandidaturaBuilder();
    }

    public CandidaturaBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CandidaturaBuilder aprovacao(Boolean aprovacao) {
        this.aprovacao = aprovacao;
        return this;
    }

    public CandidaturaBuilder pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public Candidatura build() {
        Candidatura candidatura = new Candidatura();
        candidatura.setId(id);
        candidatura.setAprovacao(aprovacao);
        candidatura.setPessoa(pessoa);
        return candidatura;
    }
}
