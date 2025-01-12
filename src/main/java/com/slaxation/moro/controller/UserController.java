package com.slaxation.moro.controller;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.dto.UserDTO;
import com.slaxation.moro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO updateUser(@RequestBody InputUserRequestDTO userRequest) {
        return userService.updateLoggedInUser(userRequest);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody InputUserRequestDTO inputUserRequestDTO) {
        return userService.createUser(inputUserRequestDTO);
    }

    @DeleteMapping("/self-delete")
    public void deleteOwnUserAccount(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getUsername());
    }
}
