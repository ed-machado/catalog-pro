package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.auth.Login;
import com.machado.catalog_prod.entity.User;
import com.machado.catalog_prod.repositories.LoginRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        // Ensure the user exists in the database
        if (!loginRepository.existsByUsername("admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("ADMIN");
            loginRepository.save(user);
        }

        // Perform login to get the token
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();
        String loginRequest = objectMapper.writeValueAsString(login);

        token = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void testCreateProduct() throws Exception {
        String productRequest = "{\"name\":\"Laptop\",\"description\":\"Gaming Laptop\",\"price\":1500.0,\"category\":{\"name\":\"Electronics\",\"description\":\"Electronic items\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product created successfully."));
    }
}