package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.exceptions.UserNotFoundException;
import com.supershaun.bikeshop.models.User;
import com.supershaun.bikeshop.repositories.UserRepository;
import com.supershaun.bikeshop.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return JwtUserDetails.build(user);
    }
}
