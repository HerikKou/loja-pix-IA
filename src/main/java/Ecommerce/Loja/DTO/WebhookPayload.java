package Ecommerce.Loja.DTO;
import Ecommerce.Loja.DTO.WebhookData;
public class WebhookPayload {

    private String type;
    private WebhookData data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebhookData getData() {
        return data;
    }

    public void setData(WebhookData data) {
        this.data = data;
    }
}