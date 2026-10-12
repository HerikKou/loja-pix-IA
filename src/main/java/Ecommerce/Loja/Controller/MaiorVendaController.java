package Ecommerce.Loja.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.Loja.DTO.MaiorTotalVendido;
import Ecommerce.Loja.Service.MaiorTotalVendidoService;

@RestController 
@RequestMapping("/api")
public class MaiorVendaController {
    private final MaiorTotalVendidoService vendaService;

    public MaiorVendaController(MaiorTotalVendidoService vendaService) {
        this.vendaService = vendaService;
    }
    @GetMapping("/maiorVenda")
    public ResponseEntity<List<MaiorTotalVendido>> maiorVenda(){
        List<MaiorTotalVendido> maiorVenda = vendaService.maiorTotalVendido();
        return ResponseEntity.status(200).body(maiorVenda);
    }
}
