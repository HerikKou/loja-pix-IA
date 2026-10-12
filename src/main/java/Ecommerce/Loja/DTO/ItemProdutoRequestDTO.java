package Ecommerce.Loja.DTO;

public class ItemProdutoRequestDTO {

    private Long produtoId;
    private Integer quantidade;

    public ItemProdutoRequestDTO() {
    }

    public ItemProdutoRequestDTO(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}