package com.learncollab.softalk.web.security.config;

import com.learncollab.softalk.web.security.JwtTokenProvider;
import com.learncollab.softalk.web.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final String[] allowedUrls = {"/auth/join", "/auth/login","/", "/login", "/join",
            "/auth/email/code-request"};
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable().httpBasic().disable()

                .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(allowedUrls).permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .logout(withDefaults());

        return http.build();
    }
}
