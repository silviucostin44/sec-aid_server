package com.example.secaidserver.repository;

import com.example.secaidserver.model.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    void deleteByUsername(String username);
}
