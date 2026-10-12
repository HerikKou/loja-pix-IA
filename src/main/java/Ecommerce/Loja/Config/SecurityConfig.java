package Ecommerce.Loja.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((HttpServletRequest request, HttpServletResponse response,AccessDeniedException ex) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                })
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cadastrarCliente", "/api/loginCliente").permitAll()
                .requestMatchers("/api/loginFuncionario").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/cadastrarFuncionario").hasRole("GERENTE")

                .requestMatchers("/api/cadastrarProduto**").hasAnyRole("VENDEDOR", "GERENTE")
                
                .requestMatchers("/api/realizarTransacao").hasAnyRole("VENDEDOR", "GERENTE")

                .requestMatchers("/api/pagamento**").hasAnyRole("VENDEDOR", "GERENTE")
                .requestMatchers("/api/pagamento/{id}/cobrar**").hasAnyRole("VENDEDOR", "GERENTE")
                .requestMatchers("/api/produtos").permitAll()
                .requestMatchers("/api/notificacao").hasAnyRole("VENDEDOR", "GERENTE")
                .requestMatchers("/api/webhooks/mercadopago").permitAll()
                .requestMatchers("/api/listarFuncionarios").hasRole("GERENTE")
                .requestMatchers("/api/maiorVenda").hasRole("GERENTE")
                .requestMatchers("/api/{id}/historicovendas").hasAnyRole("VENDEDOR", "GERENTE")
                .anyRequest().authenticated()
            )
            .addFilterAt(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}