package com.project.booksocialnetwork.users;

import com.project.booksocialnetwork.users.data.dto.RegistrationRequest;
import com.project.booksocialnetwork.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }
}
