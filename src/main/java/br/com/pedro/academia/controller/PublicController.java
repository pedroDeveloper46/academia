package br.com.pedro.academia.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.academia.model.Credenciais;
import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.repository.ProfessorRepository;
import br.com.pedro.academia.service.ProfessorService;
import lombok.experimental.var;

@RestController
@RequestMapping(path="/public/professor")
@var
public class PublicController {
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/logar")
	public ResponseEntity<Boolean> logar(@RequestBody Credenciais credenciais) {
		
		Professor professor = professorRepository.findByEmail(credenciais.getEmail());
        if (professor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        
        
        boolean valid = encoder.matches(credenciais.getSenha(), professor.getSenha());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
	}
	
	
	@PostMapping("/cadastrar")
	public Professor saveProfessor(@RequestBody @Valid Professor professor, Errors erros) {
		
		if (erros.hasErrors()) {
			throw new ValidationException("Erro de validação: " + erros.getFieldError().getDefaultMessage());
		}
		
		return professorService.saveProfessor(professor); 
	}

}
