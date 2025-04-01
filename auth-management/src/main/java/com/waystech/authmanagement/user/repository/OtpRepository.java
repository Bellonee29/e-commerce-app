package com.waystech.authmanagement.user.repository;

import com.waystech.authmanagement.user.models.Otp;
import com.waystech.authmanagement.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    boolean existsByUser(User user);
    Otp findByUser(User user);

    Optional<Otp> findByOtpAndUser(String otp, User user);

    void deleteByUser(User user);

}
