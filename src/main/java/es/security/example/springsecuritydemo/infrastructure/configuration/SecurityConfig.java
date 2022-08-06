package es.security.example.springsecuritydemo.infrastructure.configuration;

import es.security.example.springsecuritydemo.infrastructure.components.SecurityErrorNotifierComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityErrorNotifierComponent securityErrorNotifierComponent;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/public/**").permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

        //h2 console config
        http.headers().frameOptions().sameOrigin();

        //Custom handlers
        http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, res, ex) -> securityErrorNotifierComponent.reportsSecurityError(req, res, ex, HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(
                        (req, res, ex) -> securityErrorNotifierComponent.reportsSecurityError(req, res, ex, HttpStatus.FORBIDDEN));

        return http.build();
    }
}
