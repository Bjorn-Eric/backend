package org.example.eric.service;

import jakarta.transaction.Transactional;
import org.example.eric.dto.UserDTO;
import org.example.eric.model.User;
import org.example.eric.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user;
    }

    @Transactional
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUserDetailsById(Long user_id) throws AccessDeniedException {
        return userRepository.findById(user_id).orElse(null);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserById(Long userId) throws AccessDeniedException {
        userRepository.deleteById(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public int deactivateUserById(User user, Long userId) throws AccessDeniedException {
        int ans = userRepository.deactivateById(userId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(user.getUsername())) {
            SecurityContextHolder.clearContext();
        }

        return ans;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public int activateUserById(Long userId) throws AccessDeniedException {
        return userRepository.activateById(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addUser(UserDTO user) throws AccessDeniedException {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new AccessDeniedException("Username already exists");
        }

        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), User.Role.valueOf(user.getRole()));
        userRepository.save(newUser);
    }
}
