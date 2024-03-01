package br.com.redemobconsorcio.rocket.service;

import br.com.redemobconsorcio.rocket.dto.MunicpioDTO;
import br.com.redemobconsorcio.rocket.entity.Pessoa;
import br.com.redemobconsorcio.rocket.exception.RocketNegocioException;
import br.com.redemobconsorcio.rocket.repository.PessoaRepository;
import br.com.redemobconsorcio.rocket.util.CPFUtil;
import br.com.redemobconsorcio.rocket.util.MunicipioUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.redemobconsorcio.rocket.util.Constantes.*;
import static java.util.Objects.nonNull;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa, Map<String, MultipartFile> fotos) throws RocketNegocioException, IOException {
        validarPessoa(pessoa);

        MultipartFile fileRosto = fotos.get(FILE_ROSTO_KEY_MAP);
        MultipartFile fileFrenteIdentidade = fotos.get(FILE_FRENTE_IDENTIDADE_MAP);
        MultipartFile fileVersoIdentidade = fotos.get(FILE_VERSO_IDENTIDADE_KEY_MAP);
        MultipartFile fileFrenteMoradia = fotos.get(FILE_FRENTE_MORADIA_MAP);
        MultipartFile fileVersoMoradia = fotos.get(FILE_VERSO_MORADIA_MAP);

        setFotoRosto(pessoa, fileRosto);
        setFotoIdentidadeFrente(pessoa, fileFrenteIdentidade);
        setFotoIdentidadeVerso(pessoa, fileVersoIdentidade);
        setFotoMoradiaFrente(pessoa, fileFrenteMoradia);
        setFotoMoradiaVerso(pessoa, fileVersoMoradia);

        pessoa.setCpf(CPFUtil.removerMascara(pessoa.getCpf()));

        return pessoaRepository.saveAndFlush(pessoa);
    }

    public Pessoa findByCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }

    private void setFotoMoradiaVerso(Pessoa pessoa, MultipartFile fileVersoMoradia) throws IOException {
        if (fileVersoMoradia.getSize() > 0) {
            var extensao = fileVersoMoradia.getContentType().split(REGEX_SPLIT_CONTENT_TYPE_FOTO)[1];
            var base64 = DATA_IMAGE + extensao + BASE_64 + Base64.encodeBase64String(fileVersoMoradia.getBytes());
            pessoa.setFotoVersoMoradia(base64);
            pessoa.setTipoFotoVersoMoradia(fileVersoMoradia.getContentType());
            pessoa.setNomeFotoVersoMoradia(fileVersoMoradia.getOriginalFilename());
        } else {
            if (pessoa.getId() != null && pessoa.getId() > 0) {
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFotoVersoMoradia(pessoaTemp.getFotoVersoMoradia());
                pessoa.setTipoFotoVersoMoradia(pessoaTemp.getTipoFotoVersoMoradia());
                pessoa.setNomeFotoVersoMoradia(pessoaTemp.getNomeFotoVersoMoradia());
            }
        }
    }

    private void setFotoMoradiaFrente(Pessoa pessoa, MultipartFile fileFrenteMoradia) throws IOException {
        if (fileFrenteMoradia.getSize() > 0) {
            var extensao = fileFrenteMoradia.getContentType().split(REGEX_SPLIT_CONTENT_TYPE_FOTO)[1];
            var base64 = DATA_IMAGE + extensao + BASE_64 + Base64.encodeBase64String(fileFrenteMoradia.getBytes());
            pessoa.setFotoFrenteMoradia(base64);
            pessoa.setTipoFotoFrenteMoradia(fileFrenteMoradia.getContentType());
            pessoa.setNomeFotoFrenteMoradia(fileFrenteMoradia.getOriginalFilename());
        } else {
            if (nonNull(pessoa.getId()) && pessoa.getId() > 0) {
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFotoFrenteMoradia(pessoaTemp.getFotoFrenteMoradia());
                pessoa.setTipoFotoFrenteMoradia(pessoaTemp.getTipoFotoFrenteMoradia());
                pessoa.setNomeFotoFrenteMoradia(pessoaTemp.getNomeFotoFrenteMoradia());
            }
        }
    }

    private void setFotoIdentidadeVerso(Pessoa pessoa, MultipartFile fileVersoIdentidade) throws IOException {
        if (fileVersoIdentidade.getSize() > 0) {
            var extensao = fileVersoIdentidade.getContentType().split(REGEX_SPLIT_CONTENT_TYPE_FOTO)[1];
            var base64 = DATA_IMAGE + extensao + BASE_64 + Base64.encodeBase64String(fileVersoIdentidade.getBytes());
            pessoa.setFotoVersoIdentidade(base64);
            pessoa.setTipoFotoVersoIdentidade(fileVersoIdentidade.getContentType());
            pessoa.setNomeFotoVersoIdentidade(fileVersoIdentidade.getOriginalFilename());
        } else {
            if (nonNull(pessoa.getId()) && pessoa.getId() > 0) {
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFotoVersoIdentidade(pessoaTemp.getFotoVersoIdentidade());
                pessoa.setTipoFotoVersoIdentidade(pessoaTemp.getTipoFotoVersoIdentidade());
                pessoa.setNomeFotoVersoIdentidade(pessoaTemp.getNomeFotoVersoIdentidade());
            }
        }
    }

    private void setFotoIdentidadeFrente(Pessoa pessoa, MultipartFile fileFrenteIdentidade) throws IOException {
        if (fileFrenteIdentidade.getSize() > 0) {
            var extensao = fileFrenteIdentidade.getContentType().split(REGEX_SPLIT_CONTENT_TYPE_FOTO)[1];
            var base64 = DATA_IMAGE + extensao + BASE_64 + Base64.encodeBase64String(fileFrenteIdentidade.getBytes());
            pessoa.setFotoFrenteIdentidade(base64);
            pessoa.setTipoFotoFrenteIdentidade(fileFrenteIdentidade.getContentType());
            pessoa.setNomeFotoFrenteIdentidade(fileFrenteIdentidade.getOriginalFilename());
        } else {
            if (nonNull(pessoa.getId()) && pessoa.getId() > 0) {
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFotoFrenteIdentidade(pessoaTemp.getFotoFrenteIdentidade());
                pessoa.setTipoFotoFrenteIdentidade(pessoaTemp.getTipoFotoFrenteIdentidade());
                pessoa.setNomeFotoFrenteIdentidade(pessoaTemp.getNomeFotoFrenteIdentidade());
            }
        }
    }

    private void setFotoRosto(Pessoa pessoa, MultipartFile fileRosto) throws IOException {
        if (fileRosto.getSize() > 0) {
            var extensao = fileRosto.getContentType().split(REGEX_SPLIT_CONTENT_TYPE_FOTO)[1];
            var base64 = DATA_IMAGE + extensao + BASE_64 + Base64.encodeBase64String(fileRosto.getBytes());
            pessoa.setFotoRosto(base64);
            pessoa.setTipoFotoRosto(fileRosto.getContentType());
            pessoa.setNomeFotoRosto(fileRosto.getOriginalFilename());
        } else {
            if (nonNull(pessoa.getId()) && pessoa.getId() > 0) {
                Pessoa pessoaTemp = pessoaRepository.findById(pessoa.getId()).get();
                pessoa.setFotoRosto(pessoaTemp.getFotoRosto());
                pessoa.setTipoFotoRosto(pessoaTemp.getTipoFotoRosto());
                pessoa.setNomeFotoRosto(pessoaTemp.getNomeFotoRosto());
            }
        }
    }

    private void validarPessoa(Pessoa pessoa) throws RocketNegocioException {
        /**
         * RN. Candidato deve ser uma pessoa física (CPF) maior de idade (idade mínima de 18 anos);
         */
        Period periodo = Period.between(pessoa.getDataNascimento(), LocalDate.now());
        if (periodo.getYears() < MAIOR_IDADE) {
            throw new RocketNegocioException("É necessário ter 18 anos ou mais para obter o cartão!");
        }

        /**
         * RN. Candidato deve morar em um dos municípios que compõe a Região Metropolitana de Goiânia
         */
        if (!MunicipioUtil.getMunicipiosGoiania()
                .stream()
                .map(MunicpioDTO::getId)
                .map(Object::toString)
                .collect(Collectors.toList())
                .contains(pessoa.getMunicipio())) {
            throw new RocketNegocioException("Candidato deve morar em um dos municípios que compõe a Região Metropolitana de Goiânia!");
        }
    }

}
