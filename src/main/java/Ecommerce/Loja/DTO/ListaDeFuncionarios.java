package Ecommerce.Loja.DTO;

import java.math.BigDecimal;

import Ecommerce.Loja.Model.Enum.TipoFuncionario;

public class ListaDeFuncionarios{
private Long id;
private String nome;
private String email;
private TipoFuncionario tipo;
private BigDecimal totalVendido;
public ListaDeFuncionarios(Long id, String nome, String email, TipoFuncionario tipo, BigDecimal totalVendido) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.tipo = tipo;
    this.totalVendido = totalVendido;
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
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public TipoFuncionario getTipo() {
    return tipo;
}
public void setTipo(TipoFuncionario tipo) {
    this.tipo = tipo;
}
public BigDecimal getTotalVendido() {
    return totalVendido;
}
public void setTotalVendido(BigDecimal totalVendido) {
    this.totalVendido = totalVendido;
}

   
   
}