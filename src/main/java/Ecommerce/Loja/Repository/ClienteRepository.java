package Ecommerce.Loja.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Ecommerce.Loja.Model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByEmail(String email);
}
