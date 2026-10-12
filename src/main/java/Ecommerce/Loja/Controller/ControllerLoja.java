package Ecommerce.Loja.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.Loja.DTO.*;
import Ecommerce.Loja.Service.NotificacaoService;
import Ecommerce.Loja.Service.PagamentoGatewayService;
import Ecommerce.Loja.Service.ProdutoService;
import Ecommerce.Loja.Service.RealizarTransacao;
import Ecommerce.Loja.Service.ServiceLoja;
import Ecommerce.Loja.Service.WebhookService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("/api")
public class ControllerLoja {
    

    private final RealizarTransacao transacaoService;
    private final ServiceLoja serviceLoja;
    private final PagamentoGatewayService pagamentoGateway;
    private final NotificacaoService notificacaoService;
    private  final ProdutoService produtoService;
   private final WebhookService webhookService;
    public ControllerLoja(RealizarTransacao transacaoService, ServiceLoja serviceLoja,
            PagamentoGatewayService pagamentoGateway, NotificacaoService notificacaoService,
            ProdutoService produtoService, WebhookService webhookService) {
        this.transacaoService = transacaoService;
        this.serviceLoja = serviceLoja;
        this.pagamentoGateway = pagamentoGateway;
        this.notificacaoService = notificacaoService;
        this.produtoService = produtoService;
        this.webhookService = webhookService;
    }
    @PostMapping("/cadastrarCliente")
    public ResponseEntity<CadastroClienteResponse> cadastrarCliente(@RequestBody CadastroClienteRequest request) {
        CadastroClienteResponse response = serviceLoja.cadastrarCliente(request);
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/cadastrarFuncionario")
    public ResponseEntity<CadastroFuncionarioResponse> cadastrarFuncionario(@RequestBody CadastroFuncionarioRequest request) {
        CadastroFuncionarioResponse response = serviceLoja.cadastrarFuncionario(request);
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/cadastrarProduto")
    public ResponseEntity<CadastrarProdutoResponse> cadastrarProduto(@RequestBody CadastrarProdutoRequest request) {
        CadastrarProdutoResponse response = serviceLoja.cadastrarProduto(request);
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/loginFuncionario")
    public ResponseEntity<LoginResponse> loginFuncionario(@RequestBody LoginFuncionarioRequest request) {
        LoginResponse response = serviceLoja.loginFuncionario(request);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping("/loginCliente")
    public ResponseEntity<LoginClienteResponse> loginCliente(@RequestBody LoginClienteResquest request) {
        LoginClienteResponse response = serviceLoja.loginCliente(request);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping("/realizarTransacao")
    public ResponseEntity<TransacaoResponseDTO> realizarTransacao(@RequestBody TransacaoRequestDTO transacaoRequest) {
        TransacaoResponseDTO response = transacaoService.realizarTransacao(transacaoRequest);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping("/pagamento")
public ResponseEntity<PagamentoResponseDTO> realizarPagamento(@RequestBody PagamentoRequestDTO request) {
    PagamentoResponseDTO response = serviceLoja.realizarPagamento(request);
    return ResponseEntity.status(201).body(response);
}

@PostMapping("/pagamento/{id}/cobrar")
public ResponseEntity<PagamentoResponseDTO> cobrarPagamento(@PathVariable Long id) {
    PagamentoResponseDTO response = serviceLoja.cobrarPagamento(id);
    return ResponseEntity.status(200).body(response);
}

@GetMapping("/notificacao")
public ResponseEntity<List<NotificacaoResponseDTO>> listarNotificacoes() {
    List<NotificacaoResponseDTO> response = notificacaoService.notificacoes();
    return ResponseEntity.status(200).body(response);
}
@GetMapping("/produtos")
public ResponseEntity<List<CadastrarProdutoResponse>> listarProdutoss() {
    List<CadastrarProdutoResponse> response = produtoService.listarProdutosCadastrados();
    return ResponseEntity.status(200).body(response);
}

@PostMapping("/webhooks/mercadopago")
public ResponseEntity<Void> receberNotificacao(@RequestBody WebhookPayload payload) {
    webhookService.processarNotificacao(payload);
    return ResponseEntity.ok().build();
}
}
