package com.sjprograming.restapi;

import com.sjprograming.restapi.auth.model.Admin;
import com.sjprograming.restapi.auth.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createDefaultAdmin(AdminRepository adminRepository,
                                         PasswordEncoder passwordEncoder) {

        return args -> {

            String username = "admin";

            if (adminRepository.findByUsername(username).isEmpty()) {

                Admin admin = new Admin();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setActive(true);

                adminRepository.save(admin);

                System.out.println("✅ DEFAULT ADMIN CREATED");
                System.out.println("👉 Username: admin");
                System.out.println("👉 Password: admin123");

            } else {
                System.out.println("ℹ️ Admin already exists. Skipping creation.");
            }
        };
    }
}
