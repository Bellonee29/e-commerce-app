package com.waystech.authmanagement.user.services.loginService;

import com.waystech.authmanagement.exceptions.classes.InvalidCredentialsException;
import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.exceptions.classes.UserUnauthorizedException;
import com.waystech.authmanagement.security.JwtAuthProvider;
import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.SignInRequest;
import com.waystech.authmanagement.user.dto.response.SignInResponse;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthProvider authProvider;

    @Override
    public NovaResponse<SignInResponse> loginUser(SignInRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Credentials");
        }
        if(!user.getIsVerified()) {
            throw new UserUnauthorizedException("Account is not verified");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
        SignInResponse signInResponse = SignInResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .accessToken(authProvider.generateAccessToken(authentication))
                .refreshToken(authProvider.generateRefreshToken(authentication))
                .build();
        return new NovaResponse<>("User Logged in Successfully", signInResponse);
    }
}
