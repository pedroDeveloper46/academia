package br.com.pedro.academia.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedro.academia.model.Professor;
import br.com.pedro.academia.userDetails.DetalhesUsuario;



public class JWTAutenticatorFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final int TOKEN_EXPIRADO = 600_000;
	
	public static final String TOKEN_SENHA = "01b06483-fc6a-46bc-b485-d567de1d9b8f";

	private AuthenticationManager authenticationManager;
	
	public JWTAutenticatorFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		DetalhesUsuario detalhesUsuario = (DetalhesUsuario) authResult.getPrincipal();
		
		String token = JWT.create().
				withSubject(detalhesUsuario.getUsername()).
				withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRADO)).
				sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.getWriter().write(token);
		response.getWriter().flush();
				
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		try {
			Professor professor = new ObjectMapper().readValue(request.getInputStream(), Professor.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(professor.getEmail(), professor.getSenha(), new ArrayList<>()));
			
		} catch (IOException e) {
			throw new RuntimeException("Falha ao tentaticar o usuário", e);
		}
	}
	
	
	
	
}
