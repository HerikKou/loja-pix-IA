package Ecommerce.Loja.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import Ecommerce.Loja.Model.Transacao;

public interface TransacaoRepository  extends JpaRepository<Transacao, Long> {
    Optional<Transacao> findById(Long id);
    @Query("SELECT p.nome, SUM(i.quantidade) FROM ItemProduto i JOIN i.produto p " +
       "WHERE i.transacao.dataHora BETWEEN :inicio AND :fim GROUP BY p.nome")
List<Object[]> resumoVendasDoDia(LocalDateTime inicio, LocalDateTime fim);
 
}
