package Ecommerce.Loja.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Ecommerce.Loja.DTO.NotificacaoResponseDTO;
import Ecommerce.Loja.Model.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    Optional<Notificacao> findById(Long id);
    @Query("SELECT new Ecommerce.Loja.DTO.NotificacaoResponseDTO(n.id, n.mensagem, n.dataGeracao, n.lida) " +
       "FROM Notificacao n ORDER BY n.dataGeracao DESC")
List<NotificacaoResponseDTO> listarNotificacoesResumo();
}
