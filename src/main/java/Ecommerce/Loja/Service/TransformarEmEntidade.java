package Ecommerce.Loja.Service;


import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import Ecommerce.Loja.Config.JwtTokenProvider;
import Ecommerce.Loja.DTO.*;

import Ecommerce.Loja.Model.*;
import Ecommerce.Loja.Model.Enum.FormaPagamento;
import Ecommerce.Loja.Model.Enum.StatusPagamento;
import Ecommerce.Loja.Repository.*;


@Service
public class TransformarEmEntidade {
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProdutoRepository produtoRepository;
    private final TransacaoRepository transacaoRepository;
    private final ItemProdutoRepository itemProdutoRepository;
    private final PagamentoRepository pagamentoRepository; 
    private final NotificacaoRepository notificacaoRepository;
   

    public TransformarEmEntidade(PasswordEncoder passwordEncoder, ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository, ProdutoRepository produtoRepository,
            TransacaoRepository transacaoRepository, ItemProdutoRepository itemProdutoRepository,
            PagamentoRepository pagamentoRepository, NotificacaoRepository notificacaoRepository  ) {
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.produtoRepository = produtoRepository;
        this.transacaoRepository = transacaoRepository;
        this.itemProdutoRepository = itemProdutoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.notificacaoRepository = notificacaoRepository;
      
    }

    public Cliente cadastrarCliente(CadastroClienteRequest cliente) {
        Cliente clienteEntidade = new Cliente();
        clienteEntidade.setNome(cliente.getNome());
        clienteEntidade.setEmail(cliente.getEmail().toLowerCase());
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        clienteEntidade.setSenha(senhaCriptografada);
        return clienteRepository.save(clienteEntidade);
    }

    public Cliente atualizarCliente(Cliente cliente, CadastroClienteRequest clienteAtualizado) {
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setEmail(clienteAtualizado.getEmail().toLowerCase());
        String senhaCriptografada = passwordEncoder.encode(clienteAtualizado.getSenha());
        cliente.setSenha(senhaCriptografada);
        return clienteRepository.save(cliente);
    }

    public Funcionario cadastrarFuncionario(CadastroFuncionarioRequest funcionario) {
        Funcionario funcionarioEntidade = new Funcionario();
        funcionarioEntidade.setNome(funcionario.getNome());
        funcionarioEntidade.setEmail(funcionario.getEmail().toLowerCase());
        String senhaCriptografada = passwordEncoder.encode(funcionario.getSenha());
        funcionarioEntidade.setSenha(senhaCriptografada);
        funcionarioEntidade.setTipo(funcionario.getTipoFuncionario());
        funcionarioEntidade.setTotalVendido(java.math.BigDecimal.ZERO);
        return funcionarioRepository.save(funcionarioEntidade);
    }

    public Funcionario atualizarFuncionario(Funcionario funcionario, CadastroFuncionarioRequest funcionarioAtualizado) {
        funcionario.setNome(funcionarioAtualizado.getNome());
        funcionario.setEmail(funcionarioAtualizado.getEmail().toLowerCase());
        String senhaCriptografada = passwordEncoder.encode(funcionarioAtualizado.getSenha());
        funcionario.setSenha(senhaCriptografada);
        return funcionarioRepository.save(funcionario);
    }
    public Produto cadastrarProduto(CadastrarProdutoRequest produto) {
        Produto produtoEntidade = new Produto();
        produtoEntidade.setNome(produto.getNome());
        produtoEntidade.setDescricao(produto.getDescricao());
        produtoEntidade.setImgem(produto.getImgem());
        produtoEntidade.setPreco(produto.getPreco());
        produtoEntidade.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        return produtoRepository.save(produtoEntidade);
    }
    public Produto atualizarProduto(Produto produto, CadastrarProdutoRequest produtoAtualizado) {
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setImgem(produtoAtualizado.getImgem());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
        return produtoRepository.save(produto);
    }
    public Funcionario loginFuncionario(LoginFuncionarioRequest request) {
        Funcionario funcionario = funcionarioRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado com o email: " + request.getEmail()));
                
        if (!passwordEncoder.matches(request.getSenha(), funcionario.getSenha())) {
            throw new RuntimeException("Senha incorreta para o email: " + request.getEmail());
        }
        
        return funcionario;
    }
    public Cliente loginCliente(LoginClienteResquest request) {
        Cliente cliente = clienteRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o email: " + request.getEmail()));
        if (!passwordEncoder.matches(request.getSenha(), cliente.getSenha())) {
            throw new RuntimeException("Senha incorreta para o email: " + request.getEmail());
        }
       
        return cliente;
    }
}
