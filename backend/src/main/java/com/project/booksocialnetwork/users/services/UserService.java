package com.project.booksocialnetwork.users.services;

import com.project.booksocialnetwork.users.data.User;
import com.project.booksocialnetwork.users.data.dto.RegistrationRequest;
import com.project.booksocialnetwork.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user with email '%s' is not found".formatted(email)));
    }

    public void registerUser(RegistrationRequest registrationRequest) {

        User registeredUser = User.builder()
                .email(registrationRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .build();

        userRepository.save(registeredUser);
        String token = jwtUtils.generateToken(registeredUser);

        log.info("{}", token);
    }

    @Autowired
    public void setBCryptPasswordEncoder(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

}
