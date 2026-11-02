package Ecommerce.Loja.DTO;


import java.util.List;

public class TransacaoRequestDTO {

    private Long clienteId;

    private List<ItemProdutoRequestDTO> itens;

    public TransacaoRequestDTO() {
    }

    public TransacaoRequestDTO(Long clienteId, List<ItemProdutoRequestDTO> itens) {
        this.clienteId = clienteId;
       
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public List<ItemProdutoRequestDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemProdutoRequestDTO> itens) {
        this.itens = itens;
    }
}
