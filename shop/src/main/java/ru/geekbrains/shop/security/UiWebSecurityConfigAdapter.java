package ru.geekbrains.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UiWebSecurityConfigAdapter(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/product").authenticated()
                .antMatchers("/user").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/user/**", "/product/**").hasRole("SUPER_ADMIN")

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/product")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/access_denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, 'true' from users where username=?")
                .authoritiesByUsernameQuery(
                        "select users.username,\n" +
                                "       roles.name\n" +
                                "from users\n" +
                                "         left join users_roles on users.id = users_roles.user_id\n" +
                                "left join roles on users_roles.role_id = roles.id\n" +
                                "where users.username = ?");
    }
}
