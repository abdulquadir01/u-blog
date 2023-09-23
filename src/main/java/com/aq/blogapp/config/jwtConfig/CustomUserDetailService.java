package com.aq.blogapp.config.jwtConfig;

import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

//    public CustomUserDetailService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Load User from DB by username
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("username or email", username));
    }
}
