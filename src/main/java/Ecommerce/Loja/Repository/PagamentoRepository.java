package Ecommerce.Loja.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import Ecommerce.Loja.Model.Pagamento;

public interface PagamentoRepository  extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByIdTransacaoGateway(String idTransacaoGateway);
}
