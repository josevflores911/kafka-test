package com.facturacion.kafka.facturacion.folder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@Tag(name = "Facturas", description = "Gestión de facturas generadas desde pedidos Kafka")
public class FacturacionController {

    private final FacturacionConsumer facturacionConsumer;

    public FacturacionController(FacturacionConsumer facturacionConsumer) {
        this.facturacionConsumer = facturacionConsumer;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas")
    public List<Factura> obtenerFacturas() {
        return facturacionConsumer.obtenerFacturas();
    }

    @PutMapping("/{index}")
    @Operation(summary = "Actualizar una factura existente")
    public Factura actualizarFactura(@PathVariable int index, @RequestBody Factura nuevaFactura) {
        return facturacionConsumer.actualizarFactura(index, nuevaFactura);
    }

    @DeleteMapping("/{index}")
    @Operation(summary = "Eliminar una factura")
    public void eliminarFactura(@PathVariable int index) {
        facturacionConsumer.eliminarFactura(index);
    }
}
