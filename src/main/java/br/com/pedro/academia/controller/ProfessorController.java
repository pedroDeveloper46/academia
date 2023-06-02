package br.com.pedro.academia.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.academia.dto.ProfessorDto;
import br.com.pedro.academia.exceptions.ProfessorNotFoundException;
import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.service.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@GetMapping("/listarProfessores")
	public List<Professor> listarProfessores() {
	   return professorService.listarProfessores();
	}
	
	@GetMapping("/listarProfessor/{cpf}")
	public ProfessorDto listarProfessor(@PathVariable String cpf) throws ProfessorNotFoundException {
	   return professorService.listarProfessor(cpf);
	}
	
	@PostMapping("/cadastrar")
	public Professor saveProfessor(@RequestBody @Valid Professor professor, Errors erros) {
		
		if (erros.hasErrors()) {
			throw new ValidationException("Erro de validação: " + erros.getFieldError().getDefaultMessage());
		}
		
		return professorService.saveProfessor(professor); 
	}
	
	@PutMapping("/atualizar/{cpf}")
	public ProfessorDto saveProfessor(@RequestBody @Valid ProfessorDto professor, Errors erros, @PathVariable String cpf) throws ProfessorNotFoundException {
		
		if (erros.hasErrors()) {
			throw new ValidationException("Erro de validação: " + erros.getFieldError().getDefaultMessage());
		}
		
		return professorService.updateProfessor(professor, cpf); 
	}

}
