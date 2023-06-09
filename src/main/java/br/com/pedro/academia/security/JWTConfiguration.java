package br.com.pedro.academia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import br.com.pedro.academia.service.DetalheUsuarioServiceImpl;

@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {
	
	
	private final DetalheUsuarioServiceImpl detalheUsuarioServiceImpl;
	
	private final PasswordEncoder encoder;
	
	public JWTConfiguration(DetalheUsuarioServiceImpl detalheUsuarioServiceImpl, PasswordEncoder encoder) {
		this.detalheUsuarioServiceImpl = detalheUsuarioServiceImpl;
		this.encoder = encoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(detalheUsuarioServiceImpl).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().authorizeRequests()
		     .antMatchers("/public/**").permitAll()
		     .anyRequest().authenticated()
		     .and()
		     .addFilter(new JWTAutenticatorFilter(authenticationManager()))
             .addFilter(new JWTValidatorFilter(authenticationManager()))
		     .sessionManagement()
		     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfiguration() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	

}
