package com.example.j3m4_backend.global.auth;

import com.example.j3m4_backend.domain.user.model.Users;
import com.example.j3m4_backend.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserById(Long id) {
        return userRepository.findById(id)
                .map(CustomUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
