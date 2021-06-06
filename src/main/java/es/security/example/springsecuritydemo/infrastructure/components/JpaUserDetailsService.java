package es.security.example.springsecuritydemo.infrastructure.components;

import es.security.example.springsecuritydemo.infrastructure.persistence.security.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Transactional //because the eager access
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name: " + username + " not found"));
    }
}
