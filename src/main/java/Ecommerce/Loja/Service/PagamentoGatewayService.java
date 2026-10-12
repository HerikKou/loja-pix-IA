package Ecommerce.Loja.Service;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Ecommerce.Loja.Model.Pagamento;

@Service
public class PagamentoGatewayService {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @PostConstruct
    public void configurar() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public Order criarCobrancaPix(Pagamento pagamento) {

        OrderClient client = new OrderClient();

        OrderPaymentRequest payment = OrderPaymentRequest.builder()
            .amount(pagamento.getValorTotal().toString())
            .paymentMethod(OrderPaymentMethodRequest.builder()
                .id("pix")
                .type("bank_transfer")
                .build())
            .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
            .type("online")
            .processingMode("automatic")
            .totalAmount(pagamento.getValorTotal().toString())
            .externalReference(pagamento.getId().toString()) 
            .payer(OrderPayerRequest.builder()
                .email(pagamento.getCliente().getEmail())
                .build())
            .transactions(OrderTransactionRequest.builder()
                .payments(List.of(payment))
                .build())
            .build();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Idempotency-Key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
            .customHeaders(headers)
            .build();

        try {
            Order order = client.create(request, requestOptions);
            pagamento.setIdTransacaoGateway(order.getId());
            return order;
        } catch (MPApiException | MPException e) {
            throw new RuntimeException("Erro ao criar cobrança no Mercado Pago: " + e.getMessage());
        }
    }
}