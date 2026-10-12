package Ecommerce.Loja.DTO;

import java.math.BigDecimal;

public class CadastrarProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private byte[] imagem;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    public CadastrarProdutoResponse() {
    }
    public CadastrarProdutoResponse(Long id, String nome, String descricao, byte[] imagem, BigDecimal preco, Integer quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public byte[] getImgem() {
        return imagem;
    }
    public void setImgem(byte[] imgem) {
        this.imagem = imgem;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
}
