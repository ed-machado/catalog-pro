package com.machado.catalog_prod.service;

import com.machado.catalog_prod.auth.Login;
import com.machado.catalog_prod.config.JwtServiceGenerator;
import com.machado.catalog_prod.entity.User;
import com.machado.catalog_prod.repositories.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private JwtServiceGenerator jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("password");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");
        user.setRole("ADMIN");

        when(loginRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        String token = loginService.login(login);

        assertEquals("jwtToken", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLogin_InvalidCredentials() {
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        assertThrows(BadCredentialsException.class, () -> loginService.login(login));
    }

    @Test
    void testLogin_UserNotFound() {
        Login login = new Login();
        login.setUsername("nonexistent");
        login.setPassword("password");

        when(loginRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> loginService.login(login));
    }

    @Test
    void testRegister_Success() {
        Login login = new Login();
        login.setUsername("newuser");
        login.setPassword("newpassword");

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("encodedPassword");
        newUser.setRole("ADMIN");

        when(loginRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        String token = loginService.register(login);

        assertEquals("jwtToken", token);
        verify(loginRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UsernameAlreadyTaken() {
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("password");

        User existingUser = new User();
        existingUser.setUsername("admin");
        existingUser.setPassword("encodedPassword");
        existingUser.setRole("ADMIN");

        when(loginRepository.findByUsername("admin")).thenReturn(Optional.of(existingUser));

        assertThrows(BadCredentialsException.class, () -> loginService.register(login));
    }

    @Test
    void testLoginWithEmptyFields() {
        Login loginWithoutUsername = new Login();
        loginWithoutUsername.setUsername("");
        loginWithoutUsername.setPassword("password");

        Login loginWithoutPassword = new Login();
        loginWithoutPassword.setUsername("admin");
        loginWithoutPassword.setPassword("");

        assertThrows(BadCredentialsException.class, () -> loginService.login(loginWithoutUsername));
        assertThrows(BadCredentialsException.class, () -> loginService.login(loginWithoutPassword));
    }

    @Test
    void testRegisterWithCaseInsensitiveUsername() {
        Login login = new Login();
        login.setUsername("Admin");
        login.setPassword("newpassword");

        User newUser = new User();
        newUser.setUsername("admin");
        newUser.setPassword("encodedPassword");
        newUser.setRole("ADMIN");

        when(loginRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        String token = loginService.register(login);

        assertEquals("jwtToken", token);
        verify(loginRepository, times(1)).save(any(User.class));
    }
}