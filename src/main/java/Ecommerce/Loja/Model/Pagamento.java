package Ecommerce.Loja.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Ecommerce.Loja.Model.Enum.FormaPagamento;
import Ecommerce.Loja.Model.Enum.StatusPagamento;
import jakarta.persistence.*;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorTotal;
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private String idTransacaoGateway;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private List<Transacao> transacoes = new ArrayList<>();
    
    public Pagamento() {
    }

    public Pagamento(BigDecimal valorTotal, FormaPagamento formaPagamento, Cliente cliente, List<Transacao> transacoes) {
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.transacoes = transacoes;
        this.status = StatusPagamento.PENDENTE;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }
    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public StatusPagamento getStatus() {
        return status;
    }
    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
    public String getIdTransacaoGateway() {
        return idTransacaoGateway;
    }
    public void setIdTransacaoGateway(String idTransacaoGateway) {
        this.idTransacaoGateway = idTransacaoGateway;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Transacao> getTransacoes() {
        return transacoes;
    }
    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}