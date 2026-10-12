package Ecommerce.Loja.Model;

import java.math.BigDecimal;

import Ecommerce.Loja.Model.Enum.TipoFuncionario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity 
public class Funcionario extends Pessoa {
  
    
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipo;

    private BigDecimal totalVendido;
    public Funcionario() {
    }
    public Funcionario(Long id, String nome, String email, String senha,  TipoFuncionario tipo) {
        super(id, nome, email, senha);
        this.tipo = tipo;
        this.totalVendido = BigDecimal.ZERO;
    }
    public TipoFuncionario getTipo() {
        return tipo;
    }
    public void setTipo(TipoFuncionario tipo) {
        if(tipo == null) {
            throw new IllegalArgumentException("O tipo de funcionário não pode ser nulo.");
        }
        this.tipo = tipo;
    }
    public BigDecimal getTotalVendido() {
        return totalVendido;
    }
    public void setTotalVendido(BigDecimal totalVendido) {
        this.totalVendido = totalVendido;
    }
    
    
    }