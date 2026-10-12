package Ecommerce.Loja.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import Ecommerce.Loja.DTO.ListaDeFuncionarios;
import Ecommerce.Loja.DTO.MaiorTotalVendido;
import Ecommerce.Loja.Model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
      Optional<Funcionario> findByEmail(String email);
      @Query("SELECT new Ecommerce.Loja.DTO.ListaDeFuncionarios(f.id, f.nome, f.email ,f.tipo,f.totalVendido)"+ "FROM Funcionario f")
      List<ListaDeFuncionarios> listartodosfuncionarios();
      @Query("SELECT new Ecommerce.Loja.DTO.MaiorTotalVendido(f.id, f.nome, f.email ,f.tipo,f.totalVendido)"+ "FROM Funcionario f ORDER BY totalVendido DESC")
      List<MaiorTotalVendido> maiorVenda();
}
