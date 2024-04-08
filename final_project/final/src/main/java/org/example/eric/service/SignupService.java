package org.example.eric.service;

import org.example.eric.controller.signup.dto.SignupFormDTO;
import org.example.eric.model.User;
import org.example.eric.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO: Implement correct error handling
    public boolean createUser(SignupFormDTO dto) {
        try {
            if (Objects.equals(dto.getPassword(), dto.getConfirmPassword())) {
                User user = new User(
                        dto.getUsername(),
                        passwordEncoder.encode(dto.getPassword()),
                        User.Role.ROLE_USER
                );
                userRepository.save(user);
                System.out.println("User: " + user.getUsername() + " created successfully!");

                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.print("Something went wrong while creating new user: ");
            System.out.println(e.getMessage());
            return false;
        }
    }

}
