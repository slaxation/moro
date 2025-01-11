package com.slaxation.moro.controller;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.dto.UserDTO;
import com.slaxation.moro.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserController userController;

    @Test
    @WithMockUser(username = "admin", password = "test")
    public void testGetUserById() throws Exception {
        // Create a mock user
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("admin")
                .name("Admin")
                .password(encoder.encode("test"))
                .build();

        // Mock the service method to return the mock user
        given(userService.getUserById(1L)).willReturn(userDTO);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/users/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect 200 OK status
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("admin"));
    }

    //change happy path
    @Test
    @WithMockUser(username = "admin", password = "test")
    public void testChangePassword() throws Exception {
        InputUserRequestDTO changedUser = InputUserRequestDTO.builder()
                .username("admin")
                .password("newpassword")
                .build();

        // Create the expected result with encoded password
        String encodedPassword = encoder.encode(changedUser.getPassword());
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username(changedUser.getUsername())
                .password(encodedPassword) // Encoded password in the response
                .build();

        given(userService.updateLoggedInUser(changedUser)).willReturn(userDTO);

        mockMvc.perform(post("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(encodedPassword));
    }

    @Test
    public void testCreateUser() throws Exception {
        InputUserRequestDTO inputDTO = InputUserRequestDTO.builder()
                .username("testuser")
                .password("newpassword")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("testuser")
                .password(encoder.encode(inputDTO.getPassword()))
                .build();

        when(userService.createUser(inputDTO)).thenReturn(userDTO);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

}
