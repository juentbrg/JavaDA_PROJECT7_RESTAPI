package com.nnk.springboot.configuration;

import com.nnk.springboot.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Constructor that injects the custom user details service.
     *
     * @param userDetailsService the service to load user-specific data.
     */
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures a DAO-based authentication provider.
     *
     * @return the configured DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides a BCrypt password encoder.
     *
     * @return the BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationManager.
     *
     * @param authConfig the authentication configuration.
     * @return the AuthenticationManager.
     * @throws Exception in case of errors during the configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Registers an event publisher to handle session events.
     *
     * @return the HttpSessionEventPublisher.
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Adds support for Spring Security dialect in Thymeleaf templates.
     *
     * @return the SpringSecurityDialect.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    /**
     * Configures HTTP security settings, including authorization, login, and logout.
     *
     * @param http the HttpSecurity object to configure.
     * @return the configured SecurityFilterChain.
     * @throws Exception in case of errors during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**").permitAll()  // Allow access to static resources
                        .requestMatchers("/app/login", "/app/error").permitAll()  // Allow access to login and error pages
                        .requestMatchers("/app/secure/**").hasAuthority("ROLE_ADMIN")  // Restrict access to secure pages to admins
                        .anyRequest().hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")  // Allow access to all other pages for users with roles
                )
                .formLogin(form -> form
                        .loginPage("/app/login")  // Custom login page
                        .defaultSuccessUrl("/bidList/list", true)  // Redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/app/logout"))  // Custom logout URL
                        .logoutSuccessUrl("/app/login?logout")  // Redirect after logout
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error")  // Custom access denied page
                );

        return http.build();
    }
}
