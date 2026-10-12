package Ecommerce.Loja.DTO;


import java.util.List;

public class TransacaoRequestDTO {

    private Long clienteId;
    private Long funcionarioId;
    private List<ItemProdutoRequestDTO> itens;

    public TransacaoRequestDTO() {
    }

    public TransacaoRequestDTO(Long clienteId, Long funcionarioId, List<ItemProdutoRequestDTO> itens) {
        this.clienteId = clienteId;
        this.funcionarioId = funcionarioId;
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public Long getFuncionarioId() {
        return funcionarioId;
    }
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
    public List<ItemProdutoRequestDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemProdutoRequestDTO> itens) {
        this.itens = itens;
    }
}