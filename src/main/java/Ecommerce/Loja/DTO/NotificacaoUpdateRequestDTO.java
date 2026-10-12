package Ecommerce.Loja.DTO;


public class NotificacaoUpdateRequestDTO {

    private boolean lida;

    public NotificacaoUpdateRequestDTO() {
    }

    public NotificacaoUpdateRequestDTO(boolean lida) {
        this.lida = lida;
    }

    public boolean isLida() {
        return lida;
    }
    public void setLida(boolean lida) {
        this.lida = lida;
    }
}