package br.com.dev.restwithspringbootudemy.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dev.restwithspringbootudemy.repositories.UserRepository;
import br.com.dev.restwithspringbootudemy.security.AccountCredentialsVO;
import br.com.dev.restwithspringbootudemy.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity sigin(@RequestBody AccountCredentialsVO data) {
		
		try {
			
			var userName = data.getUsername();
			var password = data.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			
			var user = repository.findByUserName(userName);
			
			var token = "";
			
			if (Objects.nonNull(user))
				token = tokenProvider.createToke(userName, user.getRoles());
			else
				throw new UsernameNotFoundException("Username " + userName + " not found!");
			
			Map<Object, Object> model = new HashMap<>();
			model.put("username", userName);
			model.put("token", token);
			
			return ResponseEntity.ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
		
	}

}
