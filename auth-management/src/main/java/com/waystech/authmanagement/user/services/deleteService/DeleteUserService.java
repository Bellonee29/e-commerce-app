package com.waystech.authmanagement.user.services.deleteService;

import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.OtpRepository;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Transactional
    public void onBeforeDelete(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        otpRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}