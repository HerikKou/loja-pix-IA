package Ecommerce.Loja.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ecommerce.Loja.DTO.ListaDeFuncionarios;
import Ecommerce.Loja.Repository.FuncionarioRepository;

@Service 
public class ListarFuncionariosService {
    private final FuncionarioRepository repository;

    public ListarFuncionariosService(FuncionarioRepository repository) {
        this.repository = repository;
    }
    

    public List<ListaDeFuncionarios> listarTodosFuncionarios(){
        return  repository.listartodosfuncionarios();
    }
}
