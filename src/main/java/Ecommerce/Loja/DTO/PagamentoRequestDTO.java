package Ecommerce.Loja.DTO;


public class PagamentoRequestDTO {

    private Long transacaoId;
    private String formaPagamento;

    public PagamentoRequestDTO() {
    }

    public PagamentoRequestDTO(Long transacaoId, String formaPagamento) {
        this.transacaoId = transacaoId;
        this.formaPagamento = formaPagamento;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }
    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
