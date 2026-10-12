package Ecommerce.Loja.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import Ecommerce.Loja.DTO.TransacaoResponseDTO;
import Ecommerce.Loja.Repository.TransacaoRepository;

@Service 
public class IAService {
    
    private final ChatClient chatClient; 
    private final TransacaoRepository repository;
    public IAService(ChatClient.Builder chatClientBuilder, TransacaoRepository repository){
        this.chatClient = chatClientBuilder.build();
        this.repository = repository;
    }

    public String gerarAnaliseDoDia() {
    LocalDateTime inicio = LocalDate.now().atStartOfDay();
    LocalDateTime fim = LocalDate.now().atTime(LocalTime.MAX);
    
   
    List<Object[]> resumoVendas = repository.resumoVendasDoDia(inicio, fim);
    
    
    StringBuilder sb = new StringBuilder();
    for (Object[] linha : resumoVendas) {
        String produto = (String) linha[0];
        Long qtd = (Long) linha[1];
        sb.append("- ").append(produto).append(": ").append(qtd).append(" unidades vendidas. ");
    }

    String promptTemplate = "Analise o seguinte resumo de vendas do e-commerce de hoje e crie uma mensagem motivacional consolidada: {resumo}. Gere uma mensagem curta e profissional.";

    return chatClient.prompt()
            .user(promptUser -> promptUser
                    .text(promptTemplate)
                    .param("resumo", sb.toString())
            )
            .call()
            .content();
}
}