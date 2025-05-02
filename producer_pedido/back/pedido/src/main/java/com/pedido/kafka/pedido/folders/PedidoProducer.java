package com.pedido.kafka.pedido.folders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    @Value("${topicos.pedido.request.topic}")
    String pedidoRequestTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PedidoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarEvento(String mensaje) {
        kafkaTemplate.send(pedidoRequestTopic, mensaje); // Topic: pedidos_eventos
    }

}
