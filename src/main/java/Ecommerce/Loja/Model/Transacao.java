package Ecommerce.Loja.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Ecommerce.Loja.Model.Enum.Status;
import jakarta.persistence.*;

@Entity
public class Transacao {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    @ManyToOne
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
    private Pagamento pagamento;
    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL)
    private List<ItemProduto> itens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;

    public Transacao() {
    }

    public Transacao(Long id, BigDecimal valor, LocalDateTime dataHora, Funcionario funcionario, Cliente cliente,
            List<ItemProduto> itens, Status status) {
        this.id = id;
        this.valor = valor;
        this.dataHora = dataHora;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Pagamento getPagamento() {
        return pagamento;
    }
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    public List<ItemProduto> getItens() {
        return itens;
    }
    public void setItens(List<ItemProduto> itens) {
        this.itens = itens;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}