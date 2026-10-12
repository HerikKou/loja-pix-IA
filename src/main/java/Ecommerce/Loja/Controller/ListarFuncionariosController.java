package Ecommerce.Loja.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.Loja.DTO.ListaDeFuncionarios;
import Ecommerce.Loja.Service.ListarFuncionariosService;

@RestController 
@RequestMapping("/api")
public class ListarFuncionariosController {
    
    private final ListarFuncionariosService service;
    
    
    public ListarFuncionariosController(ListarFuncionariosService service) {
        this.service = service;
    }


    @GetMapping("/listarFuncionarios")
    public ResponseEntity<List<ListaDeFuncionarios>> listarTodos(){
        List<ListaDeFuncionarios> funcionariosCadastrados = service.listarTodosFuncionarios();
        return ResponseEntity.status(200).body(funcionariosCadastrados);
    }
}
