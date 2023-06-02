package br.com.pedro.academia.service;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedro.academia.dto.ConvertDto;
import br.com.pedro.academia.dto.ProfessorDto;
import br.com.pedro.academia.exceptions.ProfessorNotFoundException;
import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.repository.ProfessorRepository;
import br.com.pedro.academia.utils.StringUtils;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private ConvertDto convertDto;
	
	public Professor saveProfessor(Professor professor) {
		
		if (StringUtils.isCPF(professor.getCpf())) {
			
			if (!validateCPF(professor.getCpf(), professor.getId())) {
				throw new ValidationException("Erro de validação: CPF já cadastrado!!!");
			}
			
			if (!validateEmail(professor.getEmail(), professor.getId())) {
				throw new ValidationException("Erro de validação: Email já cadastrado!!!");
			}
			
		}else {
			throw new ValidationException("Erro de validação: CPF inválido");
		}
		
		professor.encrypt();
		
		return professorRepository.save(professor);
		
	}
	
	public ProfessorDto listarProfessor(String cpf) throws ProfessorNotFoundException{
		
		Professor professor = professorRepository.findByCpf(cpf);
		
		if (professor == null) {
			throw new ProfessorNotFoundException();
		}
		
		return convertDto.toProfessorDto(professor);
		
	}
	
	public List<Professor> listarProfessores(){
		return professorRepository.findAll();
	}
	
	public ProfessorDto updateProfessor(ProfessorDto professor, String cpf) throws ProfessorNotFoundException {
		
		if (!StringUtils.isCPF(cpf)) {
			throw new ValidationException("Erro de validação: CPF inválido");
		}
		
		Professor prof = professorRepository.findByCpf(cpf);
		
		if (prof != null) {
			professor.setId(prof.getId());
			
			if (!StringUtils.isCPF(professor.getCpf())) {
				throw new ValidationException("Erro de validação: CPF inválido");
			}
			
			if(!validateCPF(professor.getCpf(), professor.getId())) {
				throw new ValidationException("Erro de validação: CPF já cadastrado!!!");
			}
			
			if(!validateEmail(professor.getEmail(), professor.getId())) {
				throw new ValidationException("Erro de validação: Email já cadastrado!!!");
			}
			
			Professor professorModel = convertDto.toProfessor(professor);
			
			professorModel.setSenha(prof.getSenha());
			
			professorModel = professorRepository.save(professorModel);
			
			return convertDto.toProfessorDto(professorModel);
			
		}else {
			throw new ProfessorNotFoundException();
		}
		
	}
	
	
	public boolean validateCPF(String cpf, Integer id) {
		
		Professor prof = professorRepository.findByCpf(cpf);
		
		if (prof != null) {
			
			if (id == null) {
				return false;
			}
			
		    if (!prof.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
		
	}
	
	public boolean validateEmail(String email, Integer id) {

		Professor prof = professorRepository.findByEmail(email);

		if (prof != null) {

			if (id == null) {
				return false;
			}

			if (!prof.getId().equals(id)) {
				return false;
			}
		}

		return true;

	}

}
