
package Ecommerce.Loja.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebhookSignatureService {

    private final String secret;

    public WebhookSignatureService(
            @Value("${mercadopago.webhook.secret}") String secret) {
        this.secret = secret;
    }

    public boolean validarAssinatura(
            String xSignature,
            String xRequestId,
            String dataId) {

        try {
            if (xSignature == null || xRequestId == null ||
                dataId == null || dataId.isBlank() ||
                secret == null || secret.isBlank()) {
                return false;
            }

            String timestamp = null;
            String assinaturaRecebida = null;

            for (String parte : xSignature.split(",")) {
                String[] campo = parte.trim().split("=", 2);

                if (campo.length != 2) {
                    continue;
                }

                if (campo[0].equals("ts")) {
                    timestamp = campo[1];
                } else if (campo[0].equals("v1")) {
                    assinaturaRecebida = campo[1];
                }
            }

            if (timestamp == null || assinaturaRecebida == null) {
                return false;
            }

            
            long ts = Long.parseLong(timestamp);
            long agora = System.currentTimeMillis() / 1000;

            if (Math.abs(agora - ts) > 300) {
                return false;
            }

         
            String manifesto = "id:" + dataId.toLowerCase(Locale.ROOT)
                    + ";request-id:" + xRequestId
                    + ";ts:" + timestamp + ";";

            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec chave = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(chave);

            byte[] hash = mac.doFinal(
                    manifesto.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder assinaturaCalculada = new StringBuilder();

            for (byte b : hash) {
                assinaturaCalculada.append(
                        String.format("%02x", b & 0xff)
                );
            }

            return MessageDigest.isEqual(
                    assinaturaCalculada.toString().getBytes(StandardCharsets.US_ASCII),
                    assinaturaRecebida.getBytes(StandardCharsets.US_ASCII)
            );

        } catch (Exception e) {
            return false;
        }
    }
}
