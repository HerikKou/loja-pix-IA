package Ecommerce.Loja.DTO;



import java.time.LocalDateTime;

public class NotificacaoResponseDTO {

    private Long id;
    private String mensagem;
    private LocalDateTime dataGeracao;
    private boolean lida;

    public NotificacaoResponseDTO() {
    }

    public NotificacaoResponseDTO(Long id, String mensagem, LocalDateTime dataGeracao, boolean lida) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataGeracao = dataGeracao;
        this.lida = lida;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }
    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }
    public boolean isLida() {
        return lida;
    }
    public void setLida(boolean lida) {
        this.lida = lida;
    }
}