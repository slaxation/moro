package com.slaxation.moro.service;

import com.slaxation.moro.exception.NotFoundException;
import com.slaxation.moro.exception.enumeration.GenericErrorCode;
import com.slaxation.moro.model.CustomUserDetails;
import com.slaxation.moro.model.UserEntity;
import com.slaxation.moro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Invalid username: " + username);
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        if (userEntityOptional.isEmpty()) {
            String msg = String.format("Unable to find User by username: %s", username);
            throw new NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg);
        }

        UserEntity userEntity = userEntityOptional.get();

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    public UserDetails findUserById(Long id) {

        Optional<UserEntity> u = Optional.empty();

        if (id != null) {
            u = userRepository.findUserById(id);
            if (u.isEmpty()) {
                final String msg = String.format("Unable to find User by id: %s", id);
                throw new NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg);
            }
        }

        return new CustomUserDetails(u.get());

    }
}
