package Ecommerce.Loja.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Ecommerce.Loja.Model.Funcionario;
import Ecommerce.Loja.Repository.FuncionarioRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Value("${app.gerente-inicial.email}")
    private String emailGerente;

    @Value("${app.gerente-inicial.senha}")
    private String senhaGerente;

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            FuncionarioRepository funcionarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Funcionario gerente = funcionarioRepository
                .findByEmail(emailGerente)
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));

        gerente.setSenha(passwordEncoder.encode(senhaGerente));

        funcionarioRepository.save(gerente);

        System.out.println("Senha do gerente atualizada!");
    }
}