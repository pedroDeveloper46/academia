package br.com.pedro.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pedro.academia.model.Plano;

public interface PlanoRepository extends JpaRepository<Plano, Integer> {

}
