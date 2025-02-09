package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.security.Role;
import com.example.secaidserver.model.security.UserEntity;
import com.example.secaidserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new User(userEntity.getUsername(),
                    userEntity.getPassword(),
                    buildSimpleGrantedAuthorities(Collections.singletonList(userEntity.getRole())));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
