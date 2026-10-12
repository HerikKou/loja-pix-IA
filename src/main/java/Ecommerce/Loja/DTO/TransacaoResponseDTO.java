package Ecommerce.Loja.DTO;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import Ecommerce.Loja.Model.Enum.Status;

public class TransacaoResponseDTO {

    private Long transacaoId;
    private LocalDateTime dataHora;
    private String nomeCliente;
    private String nomeFuncionario;
    private List<ItemProdutoResponseDTO> itens;
    private BigDecimal valorTotal;
    private Status status;
    private Long pagamentoId;

    public TransacaoResponseDTO() {
    }

    public TransacaoResponseDTO(Long transacaoId, LocalDateTime dataHora, String nomeCliente,
                                 String nomeFuncionario, List<ItemProdutoResponseDTO> itens,
                                 BigDecimal valorTotal, Status status, Long pagamentoId) {
        this.transacaoId = transacaoId;
        this.dataHora = dataHora;
        this.nomeCliente = nomeCliente;
        this.nomeFuncionario = nomeFuncionario;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
        this.pagamentoId = pagamentoId;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }
    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
    public List<ItemProdutoResponseDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemProdutoResponseDTO> itens) {
        this.itens = itens;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Long getPagamentoId() {
        return pagamentoId;
    }
    public void setPagamentoId(Long pagamentoId) {
        this.pagamentoId = pagamentoId;
    }
}