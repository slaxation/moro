package com.slaxation.moro.service;

import com.slaxation.moro.dto.InputUserRequestDTO;
import com.slaxation.moro.dto.UserDTO;
import com.slaxation.moro.event.UserUpdatedEvent;
import com.slaxation.moro.exception.ForbiddenException;
import com.slaxation.moro.exception.NotFoundException;
import com.slaxation.moro.exception.enumeration.GenericErrorCode;
import com.slaxation.moro.mapper.InputUserRequestMapper;
import com.slaxation.moro.mapper.UserMapper;
import com.slaxation.moro.model.UserEntity;
import com.slaxation.moro.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final InputUserRequestMapper inputUserRequestMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper, InputUserRequestMapper inputUserRequestMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.inputUserRequestMapper = inputUserRequestMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public UserDTO getUserById(Long id) {

        //retrieve UserEntity from database
        Optional<UserEntity> u = userRepository.findUserById(id);

        //Handle optional presence
        if (u.isEmpty()) {
            String msg = String.format("User with userId: " + id + " was not found.");
            throw new NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg);
        }

        return userMapper.entityToDto(u.get());

    }

    public UserDTO createUser(@Valid InputUserRequestDTO inputUserRequestDTO) {

        UserEntity user = new UserEntity();
        user.setUsername(inputUserRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(inputUserRequestDTO.getPassword()));
        userRepository.save(user);

        return userMapper.entityToDto(userRepository.save(user));
    }

    @Transactional
    public UserDTO updateLoggedInUser(@Valid InputUserRequestDTO userRequest) {

        //retrieve logged in user
        UserDetails loggedInUser = getLoggedInUser();

        //check whether logged-in user is same user as the one from updateRequest
        if(!userRequest.getUsername().equals(loggedInUser.getUsername())) {
            String msg = String.format("You need to be logged into '" + userRequest.getUsername() + "' to be able to update it!");
            throw new ForbiddenException(GenericErrorCode.FORBIDDEN_ERROR.toString(), msg);
        }

        //retrieve UserEntity from database
        Optional<UserEntity> u = userRepository.findByUsername(userRequest.getUsername());

        //Handle optional presence
        if (u.isEmpty()) {
            String msg = String.format("User: " + userRequest.getUsername() + " was not found.");
            throw new NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg);
        }

        UserEntity user = u.get();

        //patch User
        inputUserRequestMapper.entityToDto(userRequest, user);

        //persist changes
        final UserEntity savedUser = userRepository.save(user);

        //trigger event
        applicationEventPublisher.publishEvent(new UserUpdatedEvent(this, savedUser.getUsername()));

        //return updated entity
        return userMapper.entityToDto(savedUser);


    }


    private UserDetails getLoggedInUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



}
