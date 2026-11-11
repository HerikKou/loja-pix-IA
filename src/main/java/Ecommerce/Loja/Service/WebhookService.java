package Ecommerce.Loja.Service;
import org.springframework.stereotype.Service;
import Ecommerce.Loja.DTO.WebhookPayload;
import Ecommerce.Loja.Model.*;
import Ecommerce.Loja.Model.Enum.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Ecommerce.Loja.Repository.FuncionarioRepository;
import Ecommerce.Loja.Repository.PagamentoRepository;
@Service
public class WebhookService {
    private final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private final PagamentoRepository pagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public WebhookService(PagamentoRepository pagamentoRepository, FuncionarioRepository funcionarioRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void processarNotificacao(WebhookPayload payload) {

    logger.info("Tipo recebido: {}", payload.getType());

    if (!"payment".equals(payload.getType())) {
        logger.warn("Tipo de notificação ignorado: {}", payload.getType());
        return;
    }

    String idGateway = payload.getData().getId();

    logger.info("ID recebido: {}", idGateway);

    Pagamento pagamento = pagamentoRepository
        .findByIdTransacaoGateway(idGateway)
        .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

    pagamento.setStatus(StatusPagamento.PAGO);
    pagamentoRepository.save(pagamento);

    for (Transacao t : pagamento.getTransacoes()) {
        Funcionario f = t.getFuncionario();
        f.setTotalVendido(f.getTotalVendido().add(t.getValor()));
        funcionarioRepository.save(f);
    }

    logger.info("Pagamento processado com sucesso: {}", idGateway);
}
}