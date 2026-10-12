package Ecommerce.Loja.DTO;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import Ecommerce.Loja.Model.Enum.Status;
import Ecommerce.Loja.Model.Enum.StatusPagamento;

public class PagamentoResponseDTO {

    private Long pagamentoId;
    private BigDecimal valorTotal;
    private StatusPagamento status;
    private String formaPagamento;
    private LocalDateTime dataPagamento;
    private String qrCode;

    public PagamentoResponseDTO() {
    }

    public PagamentoResponseDTO(Long pagamentoId, BigDecimal valorTotal, StatusPagamento status,
                                 String formaPagamento, LocalDateTime dataPagamento, String qrCode) {
        this.pagamentoId = pagamentoId;
        this.valorTotal = valorTotal;
        this.status = status;
        this.formaPagamento = formaPagamento;
        this.dataPagamento = dataPagamento;
        this.qrCode = qrCode;
    }

    public Long getPagamentoId() {
        return pagamentoId;
    }
    public void setPagamentoId(Long pagamentoId) {
        this.pagamentoId = pagamentoId;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public StatusPagamento getStatus() {
        return status;
    }
    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }
    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
