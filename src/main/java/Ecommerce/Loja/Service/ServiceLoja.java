package Ecommerce.Loja.Service;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.Config.JwtTokenProvider;
import Ecommerce.Loja.DTO.*;
import Ecommerce.Loja.Model.*;
import Ecommerce.Loja.Repository.PagamentoRepository;
import com.mercadopago.client.order.*;
import com.mercadopago.resources.order.Order;
@Service 
public class ServiceLoja {
    private final TransformarResponse transformarResponse;
    private final TransformarEmEntidade transformarEmEntidade;
    private final PagamentoGatewayService pagamentoGatewayService;
    private final RealizarTransacao realizarTransacao;
     private final JwtTokenProvider jwtTokenProvider; 
     private final PagamentoRepository pagamentoRepository;
    public ServiceLoja(TransformarResponse transformarResponse, 
        TransformarEmEntidade transformarEmEntidade, JwtTokenProvider jwtTokenProvider, 
        PagamentoGatewayService pagamentoGatewayService, RealizarTransacao realizarTransacao, PagamentoRepository pagamentoRepository) {
        this.transformarResponse = transformarResponse;
        this.transformarEmEntidade = transformarEmEntidade;
        this.jwtTokenProvider = jwtTokenProvider;
        this.pagamentoGatewayService = pagamentoGatewayService;
        this.realizarTransacao = realizarTransacao;
        this.pagamentoRepository = pagamentoRepository;
    }
    public CadastroClienteResponse cadastrarCliente(CadastroClienteRequest request) {
        Cliente cliente = transformarEmEntidade.cadastrarCliente(request);
        return transformarResponse.transformarClienteResponse(cliente);
    }
    public CadastroFuncionarioResponse cadastrarFuncionario(CadastroFuncionarioRequest request) {
        Funcionario funcionario = transformarEmEntidade.cadastrarFuncionario(request);
        return transformarResponse.transformarFuncionarioResponse(funcionario);
    }
    public CadastrarProdutoResponse cadastrarProduto(CadastrarProdutoRequest request) {
        Produto produto = transformarEmEntidade.cadastrarProduto(request);
        return transformarResponse.transformarProdutoResponse(produto);
    }
    public LoginResponse loginFuncionario(LoginFuncionarioRequest request) {
        Funcionario funcionario = transformarEmEntidade.loginFuncionario(request);
        String token = jwtTokenProvider.gerarToken(funcionario.getId(), funcionario.getEmail(), funcionario.getTipo().toString());
        return transformarResponse.transformarLoginResponse(funcionario, token);
    }
    public LoginClienteResponse loginCliente(LoginClienteResquest request) {
        Cliente cliente = transformarEmEntidade.loginCliente(request);
        String token = jwtTokenProvider.gerarToken(cliente.getId(), cliente.getEmail(), "CLIENTE");
        return transformarResponse.transformarLoginClienteResponse(cliente, token);
    }
    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO request) {
    Pagamento pagamento = realizarTransacao.realizarPagamento(request);
    return transformarResponse.transformarPagamentoResponse(pagamento);
}

public PagamentoResponseDTO cobrarPagamento(Long pagamentoId) {
        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
            .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        Order order = pagamentoGatewayService.criarCobrancaPix(pagamento);
        pagamentoRepository.save(pagamento); 

        String qrCode = order.getTransactions()
            .getPayments()
            .get(0)
            .getPaymentMethod()
            .getQrCodeBase64();

        PagamentoResponseDTO response = transformarResponse.transformarPagamentoResponse(pagamento);
        response.setQrCode(qrCode);
        return response;
    }


    
}
