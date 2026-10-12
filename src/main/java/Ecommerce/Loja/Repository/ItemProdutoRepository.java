package Ecommerce.Loja.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Ecommerce.Loja.Model.ItemProduto;

public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Long> {
    Optional<ItemProduto> findById(Long id);
}
