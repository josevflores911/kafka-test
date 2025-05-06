package com.facturacion.kafka.facturacion;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.facturacion.kafka.facturacion.folder.Factura;
import com.facturacion.kafka.facturacion.folder.FacturacionConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

public class FacturacionConsumerTest {

    @InjectMocks
    private FacturacionConsumer facturacionConsumer; // El que vamos a probar

    @Mock
    private ConsumerRecord<String, String> mensajeMock; // Simulamos el mensaje de Kafka

    @BeforeEach
    public void setUp() {
        // Inicializamos el mock de ConsumerRecord
        MockitoAnnotations.openMocks(this);
    }

    // Test de consumirPedido() para verificar si genera una factura
    @Test
    public void testConsumirPedido() {
        // Configuramos el mensaje simulado de Kafka
        String mensaje = "Pedido creado: productoId=P001, cantidad=2, precioUnitario=100";
        when(mensajeMock.value()).thenReturn(mensaje);

        // Llamamos al método consumirPedido
        facturacionConsumer.consumirPedido(mensajeMock);

        // Verificamos que la factura se haya agregado correctamente
        List<Factura> facturas = facturacionConsumer.obtenerFacturas();
        assertEquals(1, facturas.size());
        assertEquals("P001", facturas.get(0).getProductoId());
        assertEquals(2, facturas.get(0).getCantidad());
        assertEquals(200, facturas.get(0).getTotal(), 0.01);
    }

    // Test de obtenerFacturas() para verificar que devuelve la lista correcta
    @Test
    public void testObtenerFacturas() {
        // Simulamos el consumo de un pedido
        String mensaje = "Pedido creado: productoId=P002, cantidad=5, precioUnitario=50";
        when(mensajeMock.value()).thenReturn(mensaje);
        facturacionConsumer.consumirPedido(mensajeMock);

        List<Factura> facturas = facturacionConsumer.obtenerFacturas();
        assertNotNull(facturas);
        assertTrue(facturas.size() > 0);
    }

    // Test de actualizarFactura() para verificar la actualización de una factura existente
    @Test
    public void testActualizarFactura() {
        // Simulamos la creación de una factura original
        String mensaje = "Pedido creado: productoId=P003, cantidad=1, precioUnitario=150";
        when(mensajeMock.value()).thenReturn(mensaje);
        facturacionConsumer.consumirPedido(mensajeMock); // Asumiendo que consumirPedido añade la factura

        // Creamos la factura actualizada
        Factura facturaOriginal = facturacionConsumer.obtenerFacturas().get(0);
        Factura facturaActualizada = new Factura(facturaOriginal.getProductoId(), 3, facturaOriginal.getClienteEmail(), 450);

        // Actualizamos la factura en la posición 0
        Factura resultado = facturacionConsumer.actualizarFactura(0, facturaActualizada);

        // Verificamos que la factura se haya actualizado correctamente
        assertEquals(facturaActualizada, resultado);
        assertEquals(3, facturacionConsumer.obtenerFacturas().get(0).getCantidad());
        assertEquals(450, facturacionConsumer.obtenerFacturas().get(0).getTotal(), 0.01);
    }

    // Test de eliminarFactura() para verificar que elimina una factura existente
    @Test
    public void testEliminarFactura() {
        // Agregamos una factura
        String mensaje = "Pedido creado: productoId=P004, cantidad=10, precioUnitario=20";
        when(mensajeMock.value()).thenReturn(mensaje);
        facturacionConsumer.consumirPedido(mensajeMock);

        // Eliminamos la factura
        facturacionConsumer.eliminarFactura(0);

        // Verificamos que la lista esté vacía después de eliminar la factura
        assertTrue(facturacionConsumer.obtenerFacturas().isEmpty());
    }

    // Test para verificar el comportamiento al intentar eliminar una factura no existente
    @Test
    public void testEliminarFacturaNoExistente() {
        // Intentamos eliminar una factura en un índice inválido
        assertThrows(IndexOutOfBoundsException.class, () -> {
            facturacionConsumer.eliminarFactura(999);  // Índice no válido
        });
    }

    // Test para verificar el comportamiento al intentar actualizar una factura no existente
    @Test
    public void testActualizarFacturaNoExistente() {
        // Intentamos actualizar una factura en un índice inválido
        Factura nuevaFactura = new Factura("P005", 2, "nuevo@dominio.com", 300);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            facturacionConsumer.actualizarFactura(999, nuevaFactura);  // Índice no válido
        });
    }

    // Test para verificar el manejo de un mensaje nulo o vacío
    @Test
    public void testConsumirPedidoMensajeVacio() {
        // Enviamos un mensaje nulo
        when(mensajeMock.value()).thenReturn(null);
        facturacionConsumer.consumirPedido(mensajeMock);

        // Verificamos que no se agregue ninguna factura
        assertTrue(facturacionConsumer.obtenerFacturas().isEmpty());
    }

    // Test para verificar el manejo de un mensaje vacío
    @Test
    public void testConsumirPedidoMensajeVacio2() {
        // Enviamos un mensaje vacío
        when(mensajeMock.value()).thenReturn("");
        facturacionConsumer.consumirPedido(mensajeMock);

        // Verificamos que no se agregue ninguna factura
        assertTrue(facturacionConsumer.obtenerFacturas().isEmpty());
    }
}
