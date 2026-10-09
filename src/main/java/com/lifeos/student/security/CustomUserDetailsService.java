package com.lifeos.student.security;

import com.lifeos.student.entity.User;
import com.lifeos.student.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String collegeEmail)
            throws UsernameNotFoundException {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getCollegeEmail())
                .password(user.getPassword())
                .authorities("ROLE_STUDENT")
                .build();
    }
}