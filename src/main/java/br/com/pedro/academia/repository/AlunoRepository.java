package br.com.pedro.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pedro.academia.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
	
	public Aluno findByCpf(String cpf);
	

}
