package com.machado.catalog_prod.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtConfig {

	public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	public static final int TOKEN_TIME = 1;
	private static final Key SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);

	public static Key getSecureKey() {
		return SECRET_KEY;
	}

}
