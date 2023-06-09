package br.com.pedro.academia.service;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedro.academia.dto.AlunoDto;
import br.com.pedro.academia.dto.ConvertDto;
import br.com.pedro.academia.exceptions.AlunoNotFoundException;
import br.com.pedro.academia.exceptions.PlanoNotFoundException;
import br.com.pedro.academia.model.Aluno;
import br.com.pedro.academia.model.Plano;
import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.repository.AlunoRepository;
import br.com.pedro.academia.repository.PlanoRepository;
import br.com.pedro.academia.utils.StringUtils;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PlanoRepository planoRepository;
	
	@Autowired
	private ConvertDto convertDto;

	public Aluno saveAluno(AlunoDto alunoDto) throws PlanoNotFoundException {
		
		Aluno aluno;

		if (StringUtils.isCPF(alunoDto.getCpf())) {

			if (!validateCPF(alunoDto.getCpf(), alunoDto.getId())) {
				throw new ValidationException("Erro de validação: CPF de Aluno já cadastrado!!!");
			}
			
			Plano plano = validatePlano(alunoDto.getPlanoId());
			
			aluno = convertDto.toAluno(alunoDto);
			
			
			if (plano != null) {
				aluno.setPlano(plano);
			}else {
				throw new PlanoNotFoundException();
			}				

		} else {
			
			throw new ValidationException("Erro de validação: CPF inválido");
		
		}

		return alunoRepository.save(aluno);

	}
	
	public List<Aluno> listarAlunos(){
		
		return alunoRepository.findAll();
		
	}
	
	public void excluirAluno(String cpf) throws AlunoNotFoundException {
		
		Aluno aluno = listarAluno(cpf);
		
		alunoRepository.delete(aluno);
		
	}
	
	public Aluno atualizarAluno(AlunoDto aluno, String cpf) throws AlunoNotFoundException {
		
		Aluno a = listarAluno(cpf);
		
		aluno.setId(a.getId());
		
		Plano plano = validatePlano(aluno.getPlanoId());
		
		if (!StringUtils.isCPF(aluno.getCpf())) {
			throw new ValidationException("Erro de validação: CPF inválido");	
		}
		
		if (!validateCPF(aluno.getCpf(), aluno.getId())) {
			throw new ValidationException("Erro de validação: CPF de Aluno já cadastrado!!!");
		}
		
		Aluno alunoModel = convertDto.toAluno(aluno);
		
		alunoModel.setPlano(plano);
		
		return alunoRepository.save(alunoModel);
		
			
	}
	
	public Aluno listarAluno(String cpf) throws AlunoNotFoundException {
		
		if (!StringUtils.isCPF(cpf)) {
			throw new ValidationException("Erro de validação: CPF inválido");
		}
		
		Aluno aluno = alunoRepository.findByCpf(cpf);
		
		if (aluno == null) {
	        throw new AlunoNotFoundException();
		}
		
		return aluno;
		
	}

	public boolean validateCPF(String cpf, Integer id) {

		Aluno aluno = alunoRepository.findByCpf(cpf);

		if (aluno != null) {

			if (id == null) {
				return false;
			}

			if (!aluno.getId().equals(id)) {
				return false;
			}
		}

		return true;

	}
	
	public Plano validatePlano(Integer id) {
	    
		Plano plano = planoRepository.findById(id).orElseThrow();
		
		if (plano != null) {
			return plano;
		}
		
		return plano;
		
	}

}
