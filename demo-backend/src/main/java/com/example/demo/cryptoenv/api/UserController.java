package com.example.demo.cryptoenv.api;

import com.example.demo.cryptoenv.api.dto.UserSearchResponse;
import com.example.demo.registration.persistence.RegisteredUserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final RegisteredUserRepository registeredUserRepository;

    public UserController(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    @GetMapping("/search")
    public List<UserSearchResponse> searchUsers(@RequestParam String q) {

        return registeredUserRepository
                .findByEmailContainingIgnoreCase(q)
                .stream()
                .map(user -> new UserSearchResponse(
                        user.getId(),
                        user.getEmail()
                ))
                .toList();
    }

}