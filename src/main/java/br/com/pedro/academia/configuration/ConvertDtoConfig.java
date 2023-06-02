package br.com.pedro.academia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.pedro.academia.dto.ConvertDto;

@Configuration
public class ConvertDtoConfig {
	
	@Bean
	public ConvertDto convertDto() {
		return new ConvertDto();
	}


}
