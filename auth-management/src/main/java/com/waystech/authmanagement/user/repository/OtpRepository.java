package org.partypal.userManagement.domain.repository;

import org.partypal.userManagement.domain.models.Otp;
import org.partypal.userManagement.domain.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepository extends MongoRepository<Otp, String> {
    boolean existsByUser(User user);
    Otp findByUser(User user);

    Optional<Otp> findByOtpAndUser(String otp, User user);

    void deleteByUser(User user);

}
