package Ecommerce.Loja.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.DTO.CadastrarProdutoResponse;
import Ecommerce.Loja.Repository.ProdutoRepository;

@Service 
public class ProdutoService {
    private  final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository){
        this.repository = repository;
    }
    public List<CadastrarProdutoResponse> listarProdutosCadastrados(){
        return repository.listarProdutoss();
    }
}
