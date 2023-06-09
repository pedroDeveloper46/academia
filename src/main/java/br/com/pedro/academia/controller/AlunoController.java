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

import br.com.pedro.academia.dto.AlunoDto;
import br.com.pedro.academia.exceptions.AlunoNotFoundException;
import br.com.pedro.academia.exceptions.PlanoNotFoundException;
import br.com.pedro.academia.model.Aluno;
import br.com.pedro.academia.service.AlunoService;

@RequestMapping("private/aluno")
@RestController
public class AlunoController {
	
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping(path="/listarAlunos")
	public List<Aluno> listarAlunos() {
		return alunoService.listarAlunos();
	}
	
	@GetMapping(path="/listarAluno/{cpf}")
	public Aluno listarAluno(@PathVariable String cpf) throws AlunoNotFoundException  {
		return alunoService.listarAluno(cpf);
	}
	
	@GetMapping(path="/excluir/{cpf}")
	public void excluirAluno(@PathVariable String cpf) throws AlunoNotFoundException  {
		alunoService.excluirAluno(cpf);
	}
	
	@PostMapping(path = "/cadastrar")
	public Aluno cadastrar(@RequestBody @Valid AlunoDto aluno, Errors erros) throws PlanoNotFoundException {
		
		if (erros.hasErrors()) {
			throw new ValidationException("Erro de validação: " + erros.getFieldError().getDefaultMessage());
		}
		
		return alunoService.saveAluno(aluno);
		
	}
	
	@PutMapping(path = "/atualizar/{cpf}")
	public Aluno atualizar(@RequestBody @Valid AlunoDto aluno, @PathVariable String cpf,Errors erros) throws PlanoNotFoundException, AlunoNotFoundException {
		
		if (erros.hasErrors()) {
			throw new ValidationException("Erro de validação: " + erros.getFieldError().getDefaultMessage());
		}
		
		return alunoService.atualizarAluno(aluno, cpf);
		
	}
	
	

}
