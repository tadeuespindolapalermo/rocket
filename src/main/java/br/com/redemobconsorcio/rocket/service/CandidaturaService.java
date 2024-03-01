package br.com.redemobconsorcio.rocket.service;

import br.com.redemobconsorcio.rocket.builder.CandidaturaBuilder;
import br.com.redemobconsorcio.rocket.entity.Candidatura;
import br.com.redemobconsorcio.rocket.entity.Pessoa;
import br.com.redemobconsorcio.rocket.exception.RocketNegocioException;
import br.com.redemobconsorcio.rocket.repository.CandidaturaRepository;
import br.com.redemobconsorcio.rocket.repository.PessoaRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;

    private final PessoaRepository pessoaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository, PessoaRepository pessoaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public void atualizarCandidatura(Boolean aprovacao, Long idCandidatura) {
        Candidatura candidatura = candidaturaRepository.findById(idCandidatura).orElseThrow();
        candidatura.setAprovacao(aprovacao);
        candidaturaRepository.save(candidatura);
    }

    @Transactional
    public void salvarCandidatura(Boolean aprovacao, Long idPessoa) throws RocketNegocioException {
        Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> new RocketNegocioException("Pessoa não encontrada!"));

        List<Candidatura> candidaturasReprovadas = candidaturaRepository.findByPessoaAndAprovacaoFalse(pessoa);

        if (candidaturasReprovadas.size() == 2) {
            throw new RocketNegocioException("O candidato já possui duas solicitaçõs recusadas e não pode mais receber o cartão!");
        }

        List<Candidatura> candidaturasAprovadas = candidaturaRepository.findByPessoaAndAprovacaoTrue(pessoa);

        if (!candidaturasAprovadas.isEmpty()) {
            throw new RocketNegocioException("O candidato possui uma solicitação aprovada e já está apto para receber o cartão!!");
        }

        Candidatura candidatura = CandidaturaBuilder
            .newBuilder()
            .aprovacao(aprovacao)
            .pessoa(pessoa)
            .build();

        candidaturaRepository.save(candidatura);
    }

    public void baixarFoto(HttpServletResponse response, String foto, Long idPessoa) throws IOException, RocketNegocioException {
        Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> new RocketNegocioException("Pessoa não encontrada!"));

        switch (foto) {
            case "fotoFrenteIdentidade":
                if (pessoa.getFotoFrenteIdentidade() != null) {
                    download(response, pessoa.getFotoFrenteIdentidade(), pessoa.getTipoFotoFrenteIdentidade(), pessoa.getNomeFotoFrenteIdentidade());
                }
                break;
            case "fotoVersoIdentidade":
                if (pessoa.getFotoVersoIdentidade() != null) {
                    download(response, pessoa.getFotoVersoIdentidade(), pessoa.getTipoFotoVersoIdentidade(), pessoa.getNomeFotoVersoIdentidade());
                }
                break;
            case "fotoFrenteMoradia":
                if (pessoa.getFotoFrenteMoradia() != null) {
                    download(response, pessoa.getFotoFrenteMoradia(), pessoa.getTipoFotoFrenteMoradia(), pessoa.getNomeFotoFrenteMoradia());
                }
                break;
            case "fotoVersoMoradia":
                if (pessoa.getFotoVersoMoradia() != null) {
                    download(response, pessoa.getFotoVersoMoradia(), pessoa.getTipoFotoVersoMoradia(), pessoa.getNomeFotoVersoMoradia());
                }
                break;
            default:
                if (pessoa.getFotoRosto() != null) {
                    download(response, pessoa.getFotoRosto(), pessoa.getTipoFotoRosto(), pessoa.getNomeFotoRosto());
                }
        }
    }

    public List<Candidatura> buscarCandidaturarPorPessoa(Pessoa pessoa) {
        return candidaturaRepository.findByPessoa(pessoa);
    }

    public List<Candidatura> buscarTodas() {
        return candidaturaRepository.findAll();
    }

    private void download(HttpServletResponse response, String foto, String tipoFoto, String nomeFoto) throws IOException {
        byte[] decodedString = Base64.decodeBase64(foto.split("\\,")[1]);
        response.setContentLengthLong(decodedString.length);
        response.setContentType(tipoFoto);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", nomeFoto);
        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(decodedString);
    }
}
