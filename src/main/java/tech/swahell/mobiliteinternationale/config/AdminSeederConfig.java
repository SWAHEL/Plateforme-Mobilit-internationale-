package tech.swahell.mobiliteinternationale.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.swahell.mobiliteinternationale.entity.Scolarite;
import tech.swahell.mobiliteinternationale.entity.Role;
import tech.swahell.mobiliteinternationale.repository.ScolariteRepository;

@Configuration
public class AdminSeederConfig {

    @Bean
    public CommandLineRunner initAdmin(ScolariteRepository scolariteRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Make sure this method exists in ScolariteRepository
            if (scolariteRepository.findByEmail("admin@admin.com").isEmpty()) {
                Scolarite admin = new Scolarite();
                admin.setFullName("Super Admin");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.SYSTEM_ADMIN); // ✅ FIXED IF ENUM VALUE IS SYS_ADMIN
                scolariteRepository.save(admin);
                System.out.println("✅ SYS_ADMIN user created.");
            } else {
                System.out.println("ℹ️ SYS_ADMIN already exists.");
            }
        };
    }
}
