package Ecommerce.Loja.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.DTO.NotificacaoResponseDTO;
import Ecommerce.Loja.Model.Notificacao;
import Ecommerce.Loja.Repository.NotificacaoRepository;

@Service 
public class NotificacaoService {
    
    private  final NotificacaoRepository repository;
  
    public NotificacaoService(NotificacaoRepository repository){
        this.repository = repository;
      
    }
    public List<NotificacaoResponseDTO>  notificacoes(){
        return repository.listarNotificacoesResumo(); 
    }
}
