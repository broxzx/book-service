package com.project.booksocialnetwork.users.services;

import com.project.booksocialnetwork.users.data.User;
import com.project.booksocialnetwork.users.data.dto.LoginRequest;
import com.project.booksocialnetwork.users.data.dto.RegistrationRequest;
import com.project.booksocialnetwork.utils.EmailService;
import com.project.booksocialnetwork.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EmailService emailService;
    private AuthenticationManager authenticationManager;

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

        sendValidationEmail(registeredUser);
    }

    private void sendValidationEmail(User user) {
        String token = jwtUtils.generateValidateEmailToken(user.getEmail());

        emailService.sendVerificationEmail("verify your email", user.getEmail(), token);
    }

    @Autowired
    public void setBCryptPasswordEncoder(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void verifyEmail(String token) {
        Claims allClaimsFromToken = jwtUtils.getAllClaimsFromToken(token);
        String verifiedEmail = allClaimsFromToken.get("verification-email").toString();

        if (verifiedEmail != null && verifiedEmail.equals("true")) {
            User user = userRepository.findByEmail(allClaimsFromToken.getSubject())
                    .orElseThrow(() -> new RuntimeException("user with email '%s' is not found".formatted(verifiedEmail)));
            user.setEnabled(true);

            userRepository.save(user);
            log.info("verified email for %s".formatted(user.getEmail()));
        }
    }

    public String loginUser(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtUtils.generateToken(userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("user with email '%s' is not found".formatted(loginRequest.getEmail()))));
    }

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user with email '%s' not found".formatted(email)));
    }
}
