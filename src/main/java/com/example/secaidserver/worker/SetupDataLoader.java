package com.example.secaidserver.worker;

import com.example.secaidserver.model.security.Role;
import com.example.secaidserver.model.security.UserEntity;
import com.example.secaidserver.repository.RoleRepository;
import com.example.secaidserver.repository.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * This class is responsible for inserting the users, roles into the db if it doesn’t exist on application startup.
 */
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // create user roles
        Role userRole = createRoleIfNotFound(Role.ROLE_USER);
        Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
        // create default users
        createUserIfNotFound("user", userRole);
        createUserIfNotFound("admin", adminRole);

        alreadySetup = true;
    }

    private Role createRoleIfNotFound(final String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }

    private void createUserIfNotFound(final String name, final Role role) {
        UserEntity user = userRepository.findByUsername(name);
        if (user == null) {
            user = new UserEntity(name, "$2a$12$.9.lepiFt33WQkZXpZ0FeOSUX9Ezp1EGafMuyIjRMC/vl0ftOh.XC", role);
            userRepository.save(user);
        }
    }
}
