package com.waystech.authmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthenticationFilter authFilter;
    private final LogoutService logoutService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(List.of("*"));
                    corsConfig.setAllowedMethods(List.of("*"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setExposedHeaders(List.of("Content-Disposition"));
                    corsConfig.setAllowCredentials(true);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfig);
                    return corsConfig;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize)->{
                    authorize.requestMatchers("/auth/register-user",
                                    "/auth/verify-otp",
                                    "/auth/resend-otp",
                                    "/auth/login-user",
                                    "/auth/forgot-password",
                                    "/auth/reset-password",
                                    "/wishlist/add-to-wishlist",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "v3/api-docs",
                                    "v3/api-docs/**", "/")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())));
        return http.build();
    }
}
