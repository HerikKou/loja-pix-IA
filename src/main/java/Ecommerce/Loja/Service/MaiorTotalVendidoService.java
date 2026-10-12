package Ecommerce.Loja.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.DTO.MaiorTotalVendido;
import Ecommerce.Loja.Repository.FuncionarioRepository;

@Service 
public class MaiorTotalVendidoService {
    private final FuncionarioRepository repository;
    
    public MaiorTotalVendidoService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<MaiorTotalVendido> maiorTotalVendido(){
        return repository.maiorVenda();
    }
}
