package com.facturacion.kafka.facturacion.folder;

import lombok.Data;

@Data
public class Factura {
    private String productoId;
    private int cantidad;
    private String clienteEmail;
    private double total;

    public Factura(String productoId, int cantidad, String clienteEmail, double total) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.clienteEmail = clienteEmail;
        this.total = total;
    }
}
