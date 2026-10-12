package Ecommerce.Loja.Model;


import java.math.BigDecimal;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity 
public class Produto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @Lob
@JdbcTypeCode(java.sql.Types.BINARY)
@Column(name = "data", columnDefinition = "BYTEA")
private byte[] imagem;
    private String nome;
    private String descricao;
    private BigDecimal preco ;
    private Integer quantidadeEstoque;
    public Produto() {
    }
    public Produto(Long id, byte[] imagem, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.descricao = descricao;
        this.preco =  preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public byte[] getImgem() {
        return imagem;
    }
    public void setImgem(byte[] imgem) {
     this.imagem = imgem;
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
            throw new IllegalArgumentException("A descrição não pode ser vazia.");
        }
        this.descricao = descricao;
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
