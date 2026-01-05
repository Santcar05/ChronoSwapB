package com.munwap.backend.seed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import com.munwap.backend.entity.UserEntity;
import com.munwap.backend.repository.RoleRepository;
import com.munwap.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
@Profile("default")
public class DataBaseInit implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
        init();
    }

    public void init() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new com.munwap.backend.entity.Role("ADMIN"));
            roleRepository.save(new com.munwap.backend.entity.Role("USER"));
        }

        // ADD 10 USERS AND 1 ADMIN
        if (userRepository.count() == 0) {
            UserEntity user = UserEntity.builder()
                    .full_name("admin")
                    .email("admin@example.com")
                    .password("admin")
                    .roles(List.of(roleRepository.findByName("ADMIN")))
                    .build();
            userRepository.save(user);

            for (int i = 1; i <= 10; i++) {
                UserEntity normalUser = UserEntity.builder()
                        .full_name("user" + i)
                        .email("user" + i + "@example.com")
                        .password("password" + i)
                        .roles(List.of(roleRepository.findByName("USER")))
                        .build();
                userRepository.save(normalUser);
            }

            // NEW Special User
            UserEntity specialUser = UserEntity.builder()
                    .full_name("John Doe")
                    .email("b7C6o@example.com")
                    .password("password")
                    .roles(List.of(roleRepository.findByName("USER")))
                    .build();
        }

    }

}
