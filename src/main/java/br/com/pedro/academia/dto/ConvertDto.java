package br.com.pedro.academia.dto;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pedro.academia.model.Professor;

public class ConvertDto {
	
     @Autowired
     private ModelMapper modelMapper;
     
     public ProfessorDto toProfessorDto(Professor professor) {
    	 return modelMapper.map(professor, ProfessorDto.class);
     }
     
     public Professor toProfessor(ProfessorDto professor) {
    	 return modelMapper.map(professor, Professor.class);
     }
     
     public List<ProfessorDto> toProfessorDtoList(List<Professor> professores){
    	 return professores.stream()
    			 .map(p -> modelMapper.map(p, ProfessorDto.class))
    			 .toList();
     }
     
     
     
     
}
