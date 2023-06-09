package br.com.pedro.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.repository.ProfessorRepository;
import br.com.pedro.academia.userDetails.DetalhesUsuario;


@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {
	
	@Autowired
	private ProfessorRepository professorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Professor professor = professorRepository.findByEmail(username);
		
		if (professor == null) {
			throw new UsernameNotFoundException("Usuário [ " + username + " ] não encontrado");
		}
		
		return new DetalhesUsuario(professor);
		
	}

	
	
}
