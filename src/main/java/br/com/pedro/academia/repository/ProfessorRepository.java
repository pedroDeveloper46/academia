package br.com.pedro.academia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pedro.academia.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
	
	public Professor findByCpf(String cpf);
	
	public Professor findByEmail(String email);

}
