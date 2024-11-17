//AuthenticationService.java
package com.machado.catalog_prod.service;

import com.machado.catalog_prod.entity.User;
import com.machado.catalog_prod.repositories.LoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.machado.catalog_prod.config.JwtServiceGenerator;
import com.machado.catalog_prod.auth.Login;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

	private final LoginRepository repository;
	private final JwtServiceGenerator jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;


	public String login(Login login) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							login.getUsername(),
							login.getPassword()
					)
			);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username or password");
		}

		Optional<User> userOptional = repository.findByUsername(login.getUsername());
		if (userOptional.isEmpty()) {
			throw new BadCredentialsException("User not found");
		}

		User user = userOptional.get();
		return jwtService.generateToken(user);
	}

	public String register(Login login) {
		System.out.println("Username: " + login.getUsername());
		System.out.println("Password: " + login.getPassword());
		Optional<User> existingUser = repository.findByUsername(login.getUsername());
		if (existingUser.isPresent()) {
			throw new BadCredentialsException("Username already taken");
		}

		User newUser = new User();
		newUser.setUsername(login.getUsername());
		newUser.setPassword(passwordEncoder.encode(login.getPassword()));
		newUser.setRole("ADMIN");

		repository.save(newUser);

		return jwtService.generateToken(newUser);
	}
}
