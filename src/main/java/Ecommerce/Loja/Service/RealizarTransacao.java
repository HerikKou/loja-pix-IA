package Ecommerce.Loja.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.UrlHandlerFilter.Builder.TrailingSlashSpec;

import Ecommerce.Loja.Config.JwtAuthFilter;
import Ecommerce.Loja.DTO.ItemProdutoResponseDTO;
import Ecommerce.Loja.DTO.PagamentoRequestDTO;
import Ecommerce.Loja.DTO.TransacaoRequestDTO;
import Ecommerce.Loja.DTO.TransacaoResponseDTO;

import Ecommerce.Loja.Model.*;
import Ecommerce.Loja.Model.Enum.FormaPagamento;
import Ecommerce.Loja.Model.Enum.Status;
import Ecommerce.Loja.Model.Enum.StatusPagamento;
import Ecommerce.Loja.Repository.ClienteRepository;
import Ecommerce.Loja.Repository.FuncionarioRepository;
import Ecommerce.Loja.Repository.PagamentoRepository;
import Ecommerce.Loja.Repository.ProdutoRepository;
import Ecommerce.Loja.Repository.TransacaoRepository;

@Service 
public class RealizarTransacao {
     private static final Logger logger = LoggerFactory.getLogger(RealizarTransacao.class);
    private final TransacaoRepository transacaoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProdutoRepository produtoRepository;
    private final PagamentoRepository pagamentoRepository;
    public RealizarTransacao(
         ClienteRepository clienteRepository, FuncionarioRepository funcionarioRepository, 
         TransacaoRepository transacaoRepository, ProdutoRepository produtoRepository, PagamentoRepository pagamentoRepository) {
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.transacaoRepository = transacaoRepository;
        this.produtoRepository = produtoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public TransacaoResponseDTO realizarTransacao(TransacaoRequestDTO transacaoRequest) {
    Cliente cliente = BuscarClientePorId(transacaoRequest.getClienteId());
      
    Funcionario funcionario = BuscarFuncionarioPorId(transacaoRequest.getFuncionarioId());

    Transacao transacao = SetarPropriedades(cliente, funcionario);
    List<ItemProduto> itens = processarItensTransacao(transacaoRequest, transacao);
    

    transacao.setItens(itens);

    BigDecimal valorTotal = calcularValorTotal(itens);
    transacao.setValor(valorTotal);

    Transacao transacaoSalva = transacaoRepository.save(transacao);
    BigDecimal subtotal = calcularSubTotal(itens);
    
     List<ItemProdutoResponseDTO> itensResponse = criarItensResponse(itens);
    Long pagamentoId = (transacaoSalva.getPagamento() != null) ? transacaoSalva.getPagamento().getId() : null;

    return criarTransacaoDTO(
        transacaoSalva.getId(), 
        LocalDateTime.now(), 
        cliente.getNome(), 
        funcionario.getNome(), 
        itensResponse, 
        valorTotal, 
        transacaoSalva.getStatus(), 
        pagamentoId
    );
       
    }


public Pagamento realizarPagamento(PagamentoRequestDTO pagamentoRequest) {
    Transacao transacao = BuscarTransacao(pagamentoRequest.getTransacaoId());

    Pagamento pagamento = new Pagamento();
    pagamento.setCliente(transacao.getCliente());
    pagamento.setValorTotal(transacao.getValor());
    pagamento.setFormaPagamento(FormaPagamento.valueOf(pagamentoRequest.getFormaPagamento()));
    pagamento.setStatus(StatusPagamento.PENDENTE);
    pagamento.setDataPagamento(LocalDateTime.now());
    pagamento.setTransacoes(List.of(transacao));
Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

    transacao.setPagamento(pagamentoSalvo);
    transacaoRepository.save(transacao);     

    return pagamentoSalvo;
}





private List<ItemProduto> processarItensTransacao(TransacaoRequestDTO transacaoRequest, Transacao transacao) {
    return transacaoRequest.getItens().stream().map(itemDTO -> {
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        
        if (produto.getQuantidadeEstoque() < itemDTO.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDTO.getQuantidade());
        produtoRepository.save(produto);

        ItemProduto item = new ItemProduto();
        item.setProduto(produto);
        item.setQuantidade(itemDTO.getQuantidade());
        item.setPrecoUnitario(produto.getPreco()); 
        item.setTransacao(transacao); 
        return item;
    }).toList();
}


private BigDecimal calcularValorTotal(List<ItemProduto> itens ){
    return  itens.stream()
        .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
}

private BigDecimal calcularSubTotal(List<ItemProduto> itens){
    return  itens.stream().map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
}


private  Transacao SetarPropriedades(Cliente cliente , Funcionario funcionario){
    Transacao transacao = new Transacao();
    transacao.setCliente(cliente);
    transacao.setFuncionario(funcionario);
    transacao.setDataHora(LocalDateTime.now());
    transacao.setStatus(Status.PENDENTE);
    return  transacao;
}




private List<ItemProdutoResponseDTO> criarItensResponse(List<ItemProduto> itens) {
    return itens.stream().map(i -> {
        
        BigDecimal subtotalItem = i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade()));
        
        return new ItemProdutoResponseDTO(
            i.getProduto().getId(),
            i.getProduto().getNome(),
            i.getQuantidade(),
            i.getPrecoUnitario(),
            subtotalItem
        );
    }).toList();
}


private TransacaoResponseDTO criarTransacaoDTO(Long transacaoId, LocalDateTime dataHora, String nomeCliente,
                                 String nomeFuncionario, List<ItemProdutoResponseDTO> itens,
                                 BigDecimal valorTotal, Status status, Long pagamentoId){
    return new TransacaoResponseDTO(transacaoId, dataHora, nomeCliente, nomeFuncionario, itens, 
        valorTotal, status, pagamentoId);

}



 private Cliente BuscarClientePorId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + clienteId));
    }

    private Funcionario BuscarFuncionarioPorId(Long funcionarioId) {
        return funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado com o ID: " + funcionarioId));
    }
    private  Transacao BuscarTransacao(Long transacaoId){
        return transacaoRepository.findById(transacaoId)
        .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }
}