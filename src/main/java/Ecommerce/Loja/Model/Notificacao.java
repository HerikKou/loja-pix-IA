package Ecommerce.Loja.Model;



import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String mensagem;

    private LocalDateTime dataGeracao;
    private boolean lida;

    public Notificacao() {
    }

    public Notificacao(String mensagem) {
        this.mensagem = mensagem;
        this.dataGeracao = LocalDateTime.now();
        this.lida = false;
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