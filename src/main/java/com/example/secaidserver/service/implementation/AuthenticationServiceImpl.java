package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.security.Role;
import com.example.secaidserver.model.security.UserEntity;
import com.example.secaidserver.repository.RoleRepository;
import com.example.secaidserver.repository.UserRepository;
import com.example.secaidserver.service.AuthenticationService;
import com.example.secaidserver.worker.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAuthenticationToken(final String username, final String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(final String username, final String password) throws IllegalArgumentException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            throw new IllegalArgumentException("There is already an account with this username: " + username);
        }

        Role role = roleRepository.findByName(Role.ROLE_USER);
        user = new UserEntity(username, password, role);
        userRepository.save(user);
    }
}
