package org.example.eric.service.userDetails;

import org.example.eric.model.User;
import org.example.eric.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User details = userRepository.findByUsername(username);

        if (details == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new org.springframework.security.core.userdetails.User(username, details.getPassword(), AuthorityUtils.createAuthorityList(details.getRole().toString()));
    }
}
