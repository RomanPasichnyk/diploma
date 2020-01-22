package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.logos.entity.UserEntity;
import ua.logos.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User with email [" + s + "] not found"));

        return User
                .builder()
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(getAuthority(entity))
                .build();
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity entity) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        entity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
}
