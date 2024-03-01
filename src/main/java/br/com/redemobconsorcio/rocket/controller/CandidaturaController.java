package br.com.redemobconsorcio.rocket.controller;

import br.com.redemobconsorcio.rocket.entity.Pessoa;
import br.com.redemobconsorcio.rocket.exception.RocketNegocioException;
import br.com.redemobconsorcio.rocket.service.CandidaturaService;
import br.com.redemobconsorcio.rocket.service.PessoaService;
import br.com.redemobconsorcio.rocket.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CandidaturaController {

	private final CandidaturaService candidaturaService;

	private final UsuarioService usuarioService;

	private final PessoaService pessoaService;

    public CandidaturaController(CandidaturaService candidaturaService, UsuarioService usuarioService, PessoaService pessoaService) {
        this.candidaturaService = candidaturaService;
        this.usuarioService = usuarioService;
        this.pessoaService = pessoaService;
    }

	@GetMapping({"**/aprovacaoCandidaturas"})
	public ModelAndView inicioAprovacao() {
		ModelAndView modelAndView = new ModelAndView("restrito/aprovacaoCandidaturas");
		modelAndView.addObject("candidaturas", candidaturaService.buscarTodas());
		return modelAndView;
	}

	@GetMapping("**/baixarFoto/{idPessoa}/{foto}")
	public void baixarFoto(
		@PathVariable("idPessoa") Long idPessoa,
		@PathVariable("foto") String foto,
		HttpServletResponse response
	) throws IOException, RocketNegocioException {
		candidaturaService.baixarFoto(response, foto, idPessoa);
	}

	@GetMapping("**/aprovacaoCandidatura/{idCandidatura}/{aprovacao}")
	public ModelAndView aprovarCandidatura(
		@PathVariable("idCandidatura") Long idCandidatura,
		@PathVariable("aprovacao") boolean aprovacao
	) {
		candidaturaService.atualizarCandidatura(aprovacao, idCandidatura);
		ModelAndView modelAndView = new ModelAndView("restrito/aprovacaoCandidaturas");
		modelAndView.addObject("candidaturas", candidaturaService.buscarTodas());
		return modelAndView;
	}

	@GetMapping({"**/consultaCandidato"})
	public ModelAndView inicioConsultaCandidatura() {
		ModelAndView modelAndView = new ModelAndView("restrito/consultaCandidato");

		String cpfUsuarioLogado = usuarioService.getCpfUsuarioLogado();
		Pessoa pessoa = pessoaService.findByCpf(cpfUsuarioLogado);

		modelAndView.addObject("candidaturas", candidaturaService.buscarCandidaturarPorPessoa(pessoa));
		modelAndView.addObject("candidato", pessoa.getNomeCompletoCandidato());

		return modelAndView;
	}

}
