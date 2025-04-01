package com.waystech.authmanagement.user.repository;

import com.waystech.authmanagement.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String userEmail);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByUsername(String username);

}
