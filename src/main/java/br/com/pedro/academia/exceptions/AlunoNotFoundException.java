package br.com.pedro.academia.exceptions;

@SuppressWarnings("serial")
public class AlunoNotFoundException extends Exception {

	
	public AlunoNotFoundException() {
		super("Aluno não encontrado");
	}
}
