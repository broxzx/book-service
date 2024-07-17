package com.project.booksocialnetwork.users;

import com.project.booksocialnetwork.users.data.dto.RegistrationRequest;
import com.project.booksocialnetwork.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
    }
}
