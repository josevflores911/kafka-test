package com.pedido.kafka.pedido.folders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class ClientePedidoController {

    private final PedidoProducer pedidoProducer;

    public ClientePedidoController(PedidoProducer pedidoProducer) {
        this.pedidoProducer = pedidoProducer;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Endpoint de pedidos ativo");
    }

    @PostMapping
    public ResponseEntity<String> crearPedido(@RequestBody PedidoRequest pedido) {
        // Aquí podrías guardar el pedido en una base de datos (si lo deseas)

        // Publicar evento a Kafka
        String evento = "Pedido creado: productoId=" + pedido.getProductoId() + ", cantidad=" + pedido.getCantidad();
        pedidoProducer.publicarEvento(evento);

        return ResponseEntity.ok("Pedido recibido y evento publicado");
    }


}
