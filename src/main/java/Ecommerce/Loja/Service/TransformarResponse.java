package Ecommerce.Loja.Service;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.Config.JwtTokenProvider;
import Ecommerce.Loja.DTO.CadastrarProdutoResponse;
import Ecommerce.Loja.DTO.CadastroClienteResponse;
import Ecommerce.Loja.DTO.CadastroFuncionarioResponse;
import Ecommerce.Loja.DTO.LoginClienteResponse;
import Ecommerce.Loja.DTO.LoginResponse;
import Ecommerce.Loja.DTO.NotificacaoResponseDTO;
import Ecommerce.Loja.DTO.PagamentoResponseDTO;
import Ecommerce.Loja.Model.*;
@Service 
public class TransformarResponse {
    private final JwtTokenProvider jwtTokenProvider;
    public TransformarResponse(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CadastrarProdutoResponse transformarProdutoResponse(Produto produto) {
        return new CadastrarProdutoResponse(produto.getId(), produto.getNome(),
         produto.getDescricao(),produto.getImgem(), produto.getPreco(), produto.getQuantidadeEstoque());
    }

    public CadastroClienteResponse transformarClienteResponse(Cliente cliente) {
        return new CadastroClienteResponse(cliente.getId(), cliente.getNome(),
         cliente.getEmail());
    }
    public CadastroFuncionarioResponse transformarFuncionarioResponse(Funcionario funcionario) {
        return new CadastroFuncionarioResponse(funcionario.getId(), funcionario.getNome(),
         funcionario.getEmail(),funcionario.getTipo(), funcionario.getTotalVendido());
    }
    public ItemProduto transformarItemProdutoResponse(ItemProduto itemProduto) {
        return new ItemProduto(itemProduto.getId(), itemProduto.getNome(), 
        itemProduto.getQuantidade(), itemProduto.getPrecoUnitario(), itemProduto.getProduto(), itemProduto.getTransacao());
    }


    public LoginClienteResponse transformarLoginClienteResponse(Cliente cliente,String token ) {
        return new LoginClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), token);
    }
    public LoginResponse transformarLoginResponse(Funcionario funcionario, String token) {
        return new LoginResponse(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(), token);
    }

    public NotificacaoResponseDTO transformarNotificacaoResponse(Notificacao notificacao) {
        return new NotificacaoResponseDTO(notificacao.getId(), notificacao.getMensagem(),notificacao.getDataGeracao(), notificacao.isLida());
    }
    public PagamentoResponseDTO transformarPagamentoResponse(Pagamento pagamento) {
    return new PagamentoResponseDTO(
        pagamento.getId(),
        pagamento.getValorTotal(),
        pagamento.getStatus(), 
        pagamento.getFormaPagamento().toString(),
        pagamento.getDataPagamento(),
        null
    );
}
}
