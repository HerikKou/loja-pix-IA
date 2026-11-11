package Ecommerce.Loja.Controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class GerarAssinatura {

    public static void main(String[] args) throws Exception {

        String secret = "307dbdbb31b08f28b856f7b2630a91be6fa9d709473ddd01c4f3b11391e84750";
        String dataId = "ORDTST01M6FBYG0Q6TGG6TWYP4H6YWSD";
        String requestId = "teste-webhook-001";

        String timestamp = String.valueOf(
            System.currentTimeMillis() / 1000
        );

        String manifesto = "id:" + dataId.toLowerCase()
                + ";request-id:" + requestId
                + ";ts:" + timestamp + ";";
        
        System.out.println("Manifesto: " + manifesto);
        Mac mac = Mac.getInstance("HmacSHA256");

        mac.init(new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
        ));

        byte[] hash = mac.doFinal(
            manifesto.getBytes(StandardCharsets.UTF_8)
        );

        StringBuilder assinatura = new StringBuilder();

        for (byte b : hash) {
            assinatura.append(String.format("%02x", b & 0xff));
        }

        System.out.println("x-request-id: " + requestId);
        System.out.println("x-signature: ts=" + timestamp
                + ",v1=" + assinatura);
    }
}