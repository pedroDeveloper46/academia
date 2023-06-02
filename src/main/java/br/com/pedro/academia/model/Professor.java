package br.com.pedro.academia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.pedro.academia.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "professor")
public class Professor {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O Nome não pode ser vazio")
	@Column(nullable = false)
	@Size(max = 50, message = "O Nome não pode conter mais de 50 caracteres")
	private String nome;
	
	@NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
    @Column(length =11, nullable = false, unique=true)
	private String cpf;
	
	@NotBlank(message = "O telefone não pode ser vazio")
	@Pattern(regexp="[0-9]{10,11}", message = "O formato do telefone é inválido")
	@Column(length = 11, nullable = false)
	private String telefone;
	
	@NotBlank(message = "O Email não pode ser vazio")
	@Size(message= "O e-mail é muito grande", max = 60)
	@Email(message = "O e-mail não é válido")
	private String email;
	
	@NotBlank(message = "A senha não pode ser vazia")
	@Size(max = 80, message = "A senha é muito grande")
	private String senha;
	
	public void encrypt() {
		this.senha = StringUtils.encrypt(senha);
	}

}
