package br.com.fatec.wave.security;

import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializerConfig {

    @Autowired
    private Environment env;

    @Value("${app.admin1.email}")
    private String admin1Email;

    @Value("${app.admin1.password}")
    private String admin1Password;

    @Value("${app.admin2.email}")
    private String admin2Email;

    @Value("${app.admin2.password}")
    private String admin2Password;

    @Bean
    public CommandLineRunner initializeData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Bloco de Debug para Variáveis de Ambiente
            System.out.println("==========================================================");
            System.out.println("INICIANDO DEBUG DE VARIÁVEIS DE AMBIENTE NO RENDER");
            System.out.println("spring.profiles.active: " + env.getProperty("spring.profiles.active"));
            System.out.println("spring.datasource.jdbc-url: " + env.getProperty("spring.datasource.jdbc-url"));
            System.out.println("spring.datasource.username: " + env.getProperty("spring.datasource.username"));
            System.out.println("spring.datasource.password is present: " + (env.getProperty("spring.datasource.password") != null && !env.getProperty("spring.datasource.password").isEmpty()));
            System.out.println("==========================================================");


            // Lógica de criação dos usuários
            // Admin 1
            if (!usuarioRepository.existsByEmail(admin1Email)) {
                Usuario admin1 = new Usuario();
                admin1.setNome("Admin Master");
                admin1.setEmail(admin1Email);
                admin1.setSenha(passwordEncoder.encode(admin1Password));
                admin1.setRole(UserRole.ADMIN);
                usuarioRepository.save(admin1);
                System.out.println(">>> Usuário Admin 1 criado com sucesso!");
            }

            // Admin 2
            if (!usuarioRepository.existsByEmail(admin2Email)) {
                Usuario admin2 = new Usuario();
                admin2.setNome("Admin Suporte");
                admin2.setEmail(admin2Email);
                admin2.setSenha(passwordEncoder.encode(admin2Password));
                admin2.setRole(UserRole.ADMIN);
                usuarioRepository.save(admin2);
                System.out.println(">>> Usuário Admin 2 criado com sucesso!");
            }
        };
    }
}
