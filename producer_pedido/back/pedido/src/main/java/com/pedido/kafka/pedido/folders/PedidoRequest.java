package com.pedido.kafka.pedido.folders;

import lombok.Data;

@Data
public class PedidoRequest {
    private String productoId;
    private int cantidad;
    private String clienteEmail;
    private int precio;
}
