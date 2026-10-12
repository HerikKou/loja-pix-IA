package Ecommerce.Loja.Model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class ItemProduto {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nome;
private Integer quantidade;
private BigDecimal precoUnitario;
@ManyToOne
@JoinColumn(name = "produto_id", referencedColumnName = "id")
private Produto produto;
@ManyToOne
@JoinColumn(name = "transacao_id", referencedColumnName = "id")
private Transacao transacao;
public ItemProduto() {

}
public ItemProduto(Long id, String nome, Integer quantidade, BigDecimal precoUnitario, Produto produto, Transacao transacao) {
    this.id = id;
    this.nome = nome;
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
    this.produto = produto;
    this.transacao = transacao;
}
public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public Integer getQuantidade() {
    return quantidade;
}
public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
}
public BigDecimal getPrecoUnitario() {
    return precoUnitario;
}
public void setPrecoUnitario(BigDecimal precoUnitario) {
    this.precoUnitario = precoUnitario;
}
public Produto getProduto() {
    return produto;
}
public void setProduto(Produto produto) {
    this.produto = produto;
}
public Transacao getTransacao() {
    return transacao;
}
public void setTransacao(Transacao transacao) {
    this.transacao = transacao;
}
public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}

}
