package com.machado.catalog_prod.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	
}
