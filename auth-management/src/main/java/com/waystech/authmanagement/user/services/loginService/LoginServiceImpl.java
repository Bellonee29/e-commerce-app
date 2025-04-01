package org.partypal.userManagement.domain.services.loginService;

import lombok.RequiredArgsConstructor;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.SignInRequest;
import org.partypal.userManagement.application.dto.response.SignInResponse;
import org.partypal.commonModule.exceptions.classes.InvalidCredentialsException;
import org.partypal.commonModule.exceptions.classes.UserNotFoundException;
import org.partypal.commonModule.exceptions.classes.UserUnauthorizedException;
import org.partypal.securityService.services.JwtAuthProvider;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.UserRepository;
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
    public PartyPalResponse<SignInResponse> loginUser(SignInRequest request){
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
        return new PartyPalResponse<>("User Logged in Successfully", signInResponse);
    }
}
