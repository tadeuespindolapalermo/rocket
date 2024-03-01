package br.com.redemobconsorcio.rocket.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "O campo 'Nome Completo do Candidato' é de preenchimento obrigatório!")
	@Column(name = "nome_completo_candidato", nullable = false)
	private String nomeCompletoCandidato;

	@NotNull(message = "O campo 'Date de Nascimento' é de preenchimento obrigatório!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "O campo 'Data de Nascimento' não deve ser uma data futura!")
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@NotEmpty(message = "O campo 'CPF' é de preenchimento obrigatório!")
	@Column(name = "cpf", length = 11, nullable = false, unique = true)
	private String cpf;

	@NotEmpty(message = "O campo 'Nome Completo da Mãe' é de preenchimento obrigatório!")
	@Column(name = "nome_completo_mae", nullable = false)
	private String nomeCompletoMae;

	@NotEmpty(message = "O campo 'Senha' é de preenchimento obrigatório!")
	@Column(name = "senha", nullable = false)
	@Size(min = 8, max = 20, message = "A senha deve conter entre 8 a 20 caracteres!")
	private String senha;

	private transient String municipio;

	@Column(name = "foto_rosto_identificacao_biometrica", nullable = false, columnDefinition = "TEXT")
	private String fotoRosto;
	@Column(name = "nome_foto_rosto_identificacao_biometrica", nullable = false)
	private String nomeFotoRosto;
	@Column(name = "tipo_foto_rosto_identificacao_biometrica", nullable = false)
	private String tipoFotoRosto;

	@Column(name = "foto_frente_documento_identificacao", nullable = false, columnDefinition = "TEXT")
	private String fotoFrenteIdentidade;
	@Column(name = "nome_foto_frente_documento_identificacao", nullable = false)
	private String nomeFotoFrenteIdentidade;
	@Column(name = "tipo_foto_frente_documento_identificacao", nullable = false)
	private String tipoFotoFrenteIdentidade;

	@Column(name = "foto_verso_documento_identificacao", nullable = false, columnDefinition = "TEXT")
	private String fotoVersoIdentidade;
	@Column(name = "nome_foto_verso_documento_identificacao", nullable = false)
	private String nomeFotoVersoIdentidade;
	@Column(name = "tipo_foto_verso_documento_identificacao", nullable = false)
	private String tipoFotoVersoIdentidade;

	@Column(name = "foto_frente_comprovacao_moradia", nullable = false, columnDefinition = "TEXT")
	private String fotoFrenteMoradia;
	@Column(name = "nome_foto_frente_comprovacao_moradia", nullable = false)
	private String nomeFotoFrenteMoradia;
	@Column(name = "tipo_foto_frente_comprovacao_moradia", nullable = false)
	private String tipoFotoFrenteMoradia;

	@Column(name = "foto_verso_comprovacao_moradia", nullable = false, columnDefinition = "TEXT")
	private String fotoVersoMoradia;
	@Column(name = "nome_foto_verso_comprovacao_moradia", nullable = false)
	private String nomeFotoVersoMoradia;
	@Column(name = "tipo_foto_verso_comprovacao_moradia", nullable = false)
	private String tipoFotoVersoMoradia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompletoCandidato() {
		return nomeCompletoCandidato;
	}

	public void setNomeCompletoCandidato(String nomeCompletoCandidato) {
		this.nomeCompletoCandidato = nomeCompletoCandidato;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCompletoMae() {
		return nomeCompletoMae;
	}

	public void setNomeCompletoMae(String nomeCompletoMae) {
		this.nomeCompletoMae = nomeCompletoMae;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFotoRosto() {
		return fotoRosto;
	}

	public void setFotoRosto(String fotoRosto) {
		this.fotoRosto = fotoRosto;
	}

	public String getNomeFotoRosto() {
		return nomeFotoRosto;
	}

	public void setNomeFotoRosto(String nomeFotoRosto) {
		this.nomeFotoRosto = nomeFotoRosto;
	}

	public String getTipoFotoRosto() {
		return tipoFotoRosto;
	}

	public void setTipoFotoRosto(String tipoFotoRosto) {
		this.tipoFotoRosto = tipoFotoRosto;
	}

	public String getFotoFrenteIdentidade() {
		return fotoFrenteIdentidade;
	}

	public void setFotoFrenteIdentidade(String fotoFrenteIdentidade) {
		this.fotoFrenteIdentidade = fotoFrenteIdentidade;
	}

	public String getNomeFotoFrenteIdentidade() {
		return nomeFotoFrenteIdentidade;
	}

	public void setNomeFotoFrenteIdentidade(String nomeFotoFrenteIdentidade) {
		this.nomeFotoFrenteIdentidade = nomeFotoFrenteIdentidade;
	}

	public String getTipoFotoFrenteIdentidade() {
		return tipoFotoFrenteIdentidade;
	}

	public void setTipoFotoFrenteIdentidade(String tipoFotoFrenteIdentidade) {
		this.tipoFotoFrenteIdentidade = tipoFotoFrenteIdentidade;
	}

	public String getFotoVersoIdentidade() {
		return fotoVersoIdentidade;
	}

	public void setFotoVersoIdentidade(String fotoVersoIdentidade) {
		this.fotoVersoIdentidade = fotoVersoIdentidade;
	}

	public String getNomeFotoVersoIdentidade() {
		return nomeFotoVersoIdentidade;
	}

	public void setNomeFotoVersoIdentidade(String nomeFotoVersoIdentidade) {
		this.nomeFotoVersoIdentidade = nomeFotoVersoIdentidade;
	}

	public String getTipoFotoVersoIdentidade() {
		return tipoFotoVersoIdentidade;
	}

	public void setTipoFotoVersoIdentidade(String tipoFotoVersoIdentidade) {
		this.tipoFotoVersoIdentidade = tipoFotoVersoIdentidade;
	}

	public String getFotoFrenteMoradia() {
		return fotoFrenteMoradia;
	}

	public void setFotoFrenteMoradia(String fotoFrenteMoradia) {
		this.fotoFrenteMoradia = fotoFrenteMoradia;
	}

	public String getNomeFotoFrenteMoradia() {
		return nomeFotoFrenteMoradia;
	}

	public void setNomeFotoFrenteMoradia(String nomeFotoFrenteMoradia) {
		this.nomeFotoFrenteMoradia = nomeFotoFrenteMoradia;
	}

	public String getTipoFotoFrenteMoradia() {
		return tipoFotoFrenteMoradia;
	}

	public void setTipoFotoFrenteMoradia(String tipoFotoFrenteMoradia) {
		this.tipoFotoFrenteMoradia = tipoFotoFrenteMoradia;
	}

	public String getFotoVersoMoradia() {
		return fotoVersoMoradia;
	}

	public void setFotoVersoMoradia(String fotoVersoMoradia) {
		this.fotoVersoMoradia = fotoVersoMoradia;
	}

	public String getNomeFotoVersoMoradia() {
		return nomeFotoVersoMoradia;
	}

	public void setNomeFotoVersoMoradia(String nomeFotoVersoMoradia) {
		this.nomeFotoVersoMoradia = nomeFotoVersoMoradia;
	}

	public String getTipoFotoVersoMoradia() {
		return tipoFotoVersoMoradia;
	}

	public void setTipoFotoVersoMoradia(String tipoFotoVersoMoradia) {
		this.tipoFotoVersoMoradia = tipoFotoVersoMoradia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
}
