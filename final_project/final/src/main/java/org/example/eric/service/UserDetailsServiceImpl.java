package org.example.eric.service;

import jakarta.transaction.Transactional;
import org.example.eric.model.User;
import org.example.eric.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User getUserDetailsById(User user, Long user_id) throws AccessDeniedException {
        if (user.getRole() == User.Role.ROLE_ADMIN) {
            return userRepository.findById(user_id).orElse(null);
        } else {
            System.out.println("Unauthorised attempt by id: " + user.getId() + " name: " + user.getUsername());
            throw new AccessDeniedException("You don't have access to this page!");
        }
    }

    @Transactional
    public void deleteUserById(User user, Long userId) throws AccessDeniedException {
        if (user.getRole() == User.Role.ROLE_ADMIN) {
            userRepository.deleteById(userId);
        } else {
            System.out.println("Unauthorised attempt by id: " + user.getId() + " name: " + user.getUsername());
            throw new AccessDeniedException("You don't have access!");
        }
    }

    @Transactional
    public int deactivateUserById(User user, Long userId) throws AccessDeniedException {
        if (user.getRole() == User.Role.ROLE_ADMIN) {
            int ans = userRepository.deactivateById(userId);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getName().equals(user.getUsername())) {
                SecurityContextHolder.clearContext();
            }

            return ans;
        } else {
            System.out.println("Unauthorised attempt by id: " + user.getId() + " name: " + user.getUsername());
            throw new AccessDeniedException("You don't have access!");
        }
    }

    @Transactional
    public int activateUserById(User user, Long userId) throws AccessDeniedException {
        if (user.getRole() == User.Role.ROLE_ADMIN) {
            return userRepository.activateById(userId);
        } else {
            System.out.println("Unauthorised attempt by id: " + user.getId() + " name: " + user.getUsername());
            throw new AccessDeniedException("You don't have access!");
        }
    }
}
