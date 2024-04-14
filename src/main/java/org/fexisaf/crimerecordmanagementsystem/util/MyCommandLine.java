package org.fexisaf.crimerecordmanagementsystem.util;

import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class MyCommandLine implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyCommandLine(UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        var userEntity = userRepository.findByEmail("test@admin.com");
        if (userEntity.isEmpty()) {

            UserEntity user = UserEntity.builder()
                    .email("test@admin.com")
                    .password(passwordEncoder.encode("SuperAdmin1"))
                    .firstName("Super")
                    .lastName("Admin")
                    .dateOfBirth("d.o.b")
                    .phoneNumber("+2347080000000")
                    .isVerified(true)
                    .role(Role.ADMIN)
                    .address("admin")
                    .build();
            userRepository.save(user);
            System.out.println("Super admin created successfully.");
        } else {

            System.out.println("Super admin 'test@admin.com' already exists. Skipping creation.");
        }
    }
}
