package com.facturacion.kafka.facturacion.folder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacturacionController {

    private final FacturacionConsumer facturacionConsumer;

    public FacturacionController(FacturacionConsumer facturacionConsumer) {
        this.facturacionConsumer = facturacionConsumer;
    }

    @GetMapping("/facturas")
    public List<Factura> obtenerFacturas() {
        return facturacionConsumer.obtenerFacturas();
    }
}
