package Ecommerce.Loja.DTO;

import java.math.BigDecimal;

public class CadastrarProdutoRequest {
    private String nome;
    private String descricao;
    private byte[] imagem;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    public CadastrarProdutoRequest() {
    }
    
    public CadastrarProdutoRequest(String nome, String descricao, byte[] imagem, BigDecimal preco,
            Integer quantidadeEstoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazio.");
        }
        this.descricao = descricao;
    }
    public byte[] getImgem() {
        return imagem;
    }
    public void setImgem(byte[] imagem) {
        if (imagem == null) {
            throw new IllegalArgumentException("A imagem não pode ser vazio.");
        }
        this.imagem = imagem;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
        this.preco = preco;
    }
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        if (quantidadeEstoque == null || quantidadeEstoque < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser nula ou negativa.");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
}
