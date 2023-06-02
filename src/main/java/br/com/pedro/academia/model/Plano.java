package br.com.pedro.academia.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Data
@Table(name="plano")
public class Plano {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotNull(message = "A descrição do plano não pode ser nula")
	@NotBlank(message = "A descrição do plano não pode ser vazia")
	@Column(length = 80)
	private String descricao;
	
	@NotNull(message = "O salário não pode ser nulo")
	private BigDecimal valor;

}
