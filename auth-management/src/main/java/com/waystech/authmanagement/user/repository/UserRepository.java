package org.partypal.userManagement.domain.repository;

import org.partypal.userManagement.domain.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String userEmail);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByUsername(String username);

}
