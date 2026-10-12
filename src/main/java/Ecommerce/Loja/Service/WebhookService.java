package Ecommerce.Loja.Service;
import org.springframework.stereotype.Service;
import Ecommerce.Loja.DTO.WebhookPayload;
import Ecommerce.Loja.Model.*;
import Ecommerce.Loja.Model.Enum.*;
import Ecommerce.Loja.Repository.FuncionarioRepository;
import Ecommerce.Loja.Repository.PagamentoRepository;
@Service
public class WebhookService {

    private final PagamentoRepository pagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public WebhookService(PagamentoRepository pagamentoRepository, FuncionarioRepository funcionarioRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void processarNotificacao(WebhookPayload payload) {
        if (!"payment".equals(payload.getType())) {
            return; 
        }

        String idGateway = payload.getData().getId();

        Pagamento pagamento = pagamentoRepository.findByIdTransacaoGateway(idGateway)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setStatus(StatusPagamento.PAGO);
        pagamentoRepository.save(pagamento);

        for (Transacao t : pagamento.getTransacoes()) {
            Funcionario f = t.getFuncionario();
            f.setTotalVendido(f.getTotalVendido().add(t.getValor()));
            funcionarioRepository.save(f);
        }
    }
}