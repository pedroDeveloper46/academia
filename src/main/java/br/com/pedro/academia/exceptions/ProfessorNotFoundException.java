package br.com.pedro.academia.exceptions;

@SuppressWarnings("serial")
public class ProfessorNotFoundException extends Exception {
	
	public ProfessorNotFoundException() {
		super("Professor não encontrado!!!");
	}

}
