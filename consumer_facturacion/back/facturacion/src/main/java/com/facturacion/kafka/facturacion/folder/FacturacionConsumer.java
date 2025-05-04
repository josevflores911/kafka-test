package com.facturacion.kafka.facturacion.folder;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableKafka
public class FacturacionConsumer {

    // Lista en memoria que almacenará las facturas
    private final List<Factura> facturas = new ArrayList<>();

    @KafkaListener(topics = "${topicos.pedido.request.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumirPedido(ConsumerRecord<String, String> mensaje) {
        String evento = mensaje.value();

        // Lógica para extraer los detalles del pedido desde el evento
        // Por ejemplo, se asume que el mensaje contiene la información en el formato "Pedido creado: productoId=123, cantidad=2"

        String[] partes = evento.split(":")[1].split(",");



        String productoId = partes[0].split("=")[1].trim();
        int cantidad = Integer.parseInt(partes[1].split("=")[1].trim());
        int precioUnitario = Integer.parseInt(partes[2].split("=")[1].trim());

        // Simulamos un cálculo de total (puedes ajustarlo según la lógica de tu negocio)
//        double precioUnitario = 100.0; // Precio fijo para simplificar el ejemplo
        double total = cantidad * precioUnitario;

        // Generamos la factura
        Factura factura = new Factura(productoId, cantidad, "cliente@dominio.com", total);
        facturas.add(factura);

        System.out.println("evento: " + evento);
        System.out.println("Factura generada: " + factura);
    }

    public List<Factura> obtenerFacturas() {
        return facturas;
    }

    public Factura actualizarFactura(int index, Factura nuevaFactura) {
        if (index >= 0 && index < facturas.size()) {
            facturas.set(index, nuevaFactura);
            return nuevaFactura;
        }
        throw new IndexOutOfBoundsException("Factura no encontrada");
    }

    public void eliminarFactura(int index) {
        if (index >= 0 && index < facturas.size()) {
            facturas.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Factura no encontrada");
        }
    }
}
