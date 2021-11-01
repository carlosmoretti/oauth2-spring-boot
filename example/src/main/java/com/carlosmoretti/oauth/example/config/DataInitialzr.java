package com.carlosmoretti.oauth.example.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.carlosmoretti.oauth.example.domain.Role;
import com.carlosmoretti.oauth.example.domain.User;
import com.carlosmoretti.oauth.example.repository.RoleRepository;
import com.carlosmoretti.oauth.example.repository.UserRepository;
import com.carlosmoretti.oauth.example.shared.constant.ProfileConst;

@Component
public class DataInitialzr implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            createUser("Admin", "admin", passwordEncoder.encode("123456"), ProfileConst.ROLE_ADMIN);
            createUser("Cliente", "cliente", passwordEncoder.encode("123456"), ProfileConst.ROLE_CLIENTE);
        }

    }

    public void createUser(String name, String email, String password, String roleName) {

        Role role = new Role(roleName);
        role = this.roleRepository.save(role);
        
        User user = new User(null, name, email, password, Arrays.asList(role));
        userRepository.save(user);
    }

}
