package ru.geekbrains.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserAuthService authService) throws Exception {

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("mem_user")
                .password(passwordEncoder.encode("user"))
                .roles("ADMIN")

                .and()
                .withUser("mem_guest")
                .password(passwordEncoder.encode("guest"))
                .roles("GUEST")

                .and()
                .withUser("mem_admin")
                .password(passwordEncoder.encode("admin"))
                .roles("SUPER_ADMIN");

        auth.userDetailsService(authService);
    }
}
