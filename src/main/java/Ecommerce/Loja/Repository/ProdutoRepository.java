package Ecommerce.Loja.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Ecommerce.Loja.DTO.CadastrarProdutoResponse;
import Ecommerce.Loja.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findById(Long id);
    @Query("SELECT new Ecommerce.Loja.DTO.CadastrarProdutoResponse(p.id, p.nome, p.descricao, p.imagem, p.preco, p.quantidadeEstoque) " +
       "FROM Produto p")
    List<CadastrarProdutoResponse> listarProdutoss();
}
