package br.com.redemobconsorcio.rocket.controller;

import br.com.redemobconsorcio.rocket.builder.UsuarioBuilder;
import br.com.redemobconsorcio.rocket.entity.Candidatura;
import br.com.redemobconsorcio.rocket.entity.Pessoa;
import br.com.redemobconsorcio.rocket.entity.Usuario;
import br.com.redemobconsorcio.rocket.exception.RocketNegocioException;
import br.com.redemobconsorcio.rocket.service.CandidaturaService;
import br.com.redemobconsorcio.rocket.service.PessoaService;
import br.com.redemobconsorcio.rocket.service.UsuarioService;
import br.com.redemobconsorcio.rocket.util.CPFUtil;
import br.com.redemobconsorcio.rocket.util.MunicipioUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
public class PessoaController {

	private final UsuarioService usuarioService;

	private final PessoaService pessoaService;

	private final CandidaturaService candidaturaService;

    public PessoaController(UsuarioService usuarioService, PessoaService pessoaService, CandidaturaService candidaturaService) {
        this.usuarioService = usuarioService;
        this.pessoaService = pessoaService;
        this.candidaturaService = candidaturaService;
    }


    @GetMapping({"**/captacaoCandidato"})
	public ModelAndView inicioCaptacaoCandidato() {
		ModelAndView modelAndView = new ModelAndView("publico/captacaoCandidato");
		modelAndView.addObject("pessoa", new Pessoa());
		modelAndView.addObject("municipios", MunicipioUtil.getMunicipiosGoiania());
		return modelAndView;
	}

	@PostMapping(value = "**/salvaPessoa", consumes = {"multipart/form-data"})
	public ModelAndView salvar(
		@Valid Pessoa pessoa, // TODO aqui é melhor usar DTO/POJO ao invés da entidade direta!
		BindingResult bindingResult,
		final MultipartFile fileRosto,
		final MultipartFile fileFrenteIdentidade,
		final MultipartFile fileVersoIdentidade,
		final MultipartFile fileFrenteMoradia,
		final MultipartFile fileVersoMoradia
	) throws IOException, RocketNegocioException {

		Pessoa pessoaConsultada = pessoaService.findByCpf(CPFUtil.removerMascara(pessoa.getCpf()));

		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("publico/captacaoCandidato");
			modelAndView.addObject("pessoa", pessoa);

			List<String> msg = new ArrayList<>();
			for (ObjectError objError : bindingResult.getAllErrors()) {
				msg.add(objError.getDefaultMessage());
			}

			modelAndView.addObject("msg", msg);
			return modelAndView;
		}

		if (nonNull(pessoaConsultada)) {
			List <Candidatura> candidaturas = candidaturaService.buscarCandidaturarPorPessoa(pessoaConsultada);
			if (candidaturas.size() > 1 && candidaturas.stream().noneMatch(c -> c.getAprovacao())) {
				throw new RocketNegocioException("Candidato atingiu o limite de duas solicitações não aprovadas!");
			}
		}

		Map<String, MultipartFile> mapFotos = Map.of(
			"fileRosto", fileRosto,
			"fileFrenteIdentidade", fileFrenteIdentidade,
			"fileVersoIdentidade", fileVersoIdentidade,
			"fileFrenteMoradia", fileFrenteMoradia,
			"fileVersoMoradia", fileVersoMoradia
		);

		Pessoa pessoaCandidatura = pessoaConsultada;
		if (isNull(pessoaConsultada)) {
			pessoaCandidatura = pessoaService.salvarPessoa(pessoa, mapFotos);

			Usuario usuario = UsuarioBuilder
				.newBuilder()
				.senha(new BCryptPasswordEncoder().encode(pessoa.getSenha()))
				.login(pessoa.getCpf())
				.build();

			usuarioService.salvarUsuario(usuario);
		}

		candidaturaService.salvarCandidatura(Boolean.FALSE, pessoaCandidatura.getId());

		ModelAndView modelAndView = new ModelAndView("publico/captacaoCandidato");
		modelAndView.addObject("pessoa", new Pessoa());
		return modelAndView;
	}
}
