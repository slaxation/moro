package com.slaxation.moro.controller;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.dto.UserDTO;
import com.slaxation.moro.mapper.InputUserRequestMapper;
import com.slaxation.moro.service.UserDetailsService;
import com.slaxation.moro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InputUserRequestMapper inputUserRequestMapper;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody InputUserRequestDTO userRequest) {
        return userService.updateLoggedInUser(userRequest);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody InputUserRequestDTO inputUserRequestDTO) {
        return userService.createUser(inputUserRequestDTO);
    }
}
