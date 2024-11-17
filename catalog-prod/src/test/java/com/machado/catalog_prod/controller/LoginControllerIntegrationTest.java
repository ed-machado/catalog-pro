package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.entity.User;
import com.machado.catalog_prod.repositories.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        loginRepository.deleteAll();
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole("ADMIN");
        loginRepository.save(user);
    }

    @Test
    void testLogin() throws Exception {
        String loginRequest = "{\"username\":\"admin\",\"password\":\"password\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testRegister() throws Exception {
        String registerRequest = "{\"username\":\"newuser\",\"password\":\"newpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginWithInvalidCredentials() throws Exception {
        String loginRequest = "{\"username\":\"admin\",\"password\":\"wrongpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegisterWithExistingUsername() throws Exception {
        String registerRequest = "{\"username\":\"admin\",\"password\":\"newpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginWithNonExistentUser() throws Exception {
        String loginRequest = "{\"username\":\"nonexistent\",\"password\":\"password\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegisterWithInvalidData() throws Exception {
        String registerRequestWithoutUsername = "{\"password\":\"newpassword\"}";
        String registerRequestWithoutPassword = "{\"username\":\"newuser\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestWithoutUsername))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestWithoutPassword))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithEmptyFields() throws Exception {
        String loginRequestWithoutUsername = "{\"username\":\"\",\"password\":\"password\"}";
        String loginRequestWithoutPassword = "{\"username\":\"admin\",\"password\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestWithoutUsername))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestWithoutPassword))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterWithEmptyFields() throws Exception {
        String registerRequestWithoutUsername = "{\"username\":\"\",\"password\":\"newpassword\"}";
        String registerRequestWithoutPassword = "{\"username\":\"newuser\",\"password\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestWithoutUsername))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestWithoutPassword))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithCaseInsensitiveUsername() throws Exception {
        String loginRequest = "{\"username\":\"Admin\",\"password\":\"password\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterWithCaseInsensitiveUsername() throws Exception {
        String registerRequest = "{\"username\":\"Admin\",\"password\":\"newpassword\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequest))
                .andExpect(status().isUnauthorized());
    }
}