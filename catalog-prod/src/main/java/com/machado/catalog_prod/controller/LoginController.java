package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.auth.Login;
import com.machado.catalog_prod.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login) {
		try {
			return ResponseEntity.ok(loginService.login(login));
		}catch(AuthenticationException ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//register endpoint
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Login login) {
		try {
			return ResponseEntity.ok(loginService.register(login));
		}catch(AuthenticationException ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

}
