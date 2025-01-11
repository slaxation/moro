package com.slaxation.moro.repository;

import com.slaxation.moro.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends GenericRepository<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findUserById(Long id);
}

