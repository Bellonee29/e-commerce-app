package com.waystech.authmanagement.security;

import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("Invalid Credentials"));
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user){
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }
}
