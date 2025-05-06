package com.facturacion.kafka.facturacion;

import com.facturacion.kafka.facturacion.folder.Factura;
import com.facturacion.kafka.facturacion.folder.FacturacionConsumer;
import com.facturacion.kafka.facturacion.folder.FacturacionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturacionControllerTest {

    private FacturacionConsumer facturacionConsumer;
    private FacturacionController controller;

    @BeforeEach
    void setUp() {
        facturacionConsumer = mock(FacturacionConsumer.class);
        controller = new FacturacionController(facturacionConsumer);
    }

    @Test
    void testObtenerFacturas() {
        // Arrange
        Factura factura1 = new Factura("F001", 12, "j@mail.com", 200);
        Factura factura2 = new Factura("F002", 2, "v@mail.com", 130);
        List<Factura> expectedFacturas = Arrays.asList(factura1, factura2);

        when(facturacionConsumer.obtenerFacturas()).thenReturn(expectedFacturas);

        // Act
        List<Factura> actualFacturas = controller.obtenerFacturas();

        // Assert
        assertEquals(expectedFacturas, actualFacturas);
        verify(facturacionConsumer, times(1)).obtenerFacturas();
    }

    @Test
    void testActualizarFactura() {
        // Arrange
        int index = 1;
        Factura nuevaFactura = new Factura("F001", 12, "j@mail.com", 200);
        Factura facturaActualizada = new Factura("F002", 2, "v@mail.com", 130);


        when(facturacionConsumer.actualizarFactura(index, nuevaFactura)).thenReturn(facturaActualizada);

        // Act
        Factura resultado = controller.actualizarFactura(index, nuevaFactura);

        // Assert
        assertEquals(facturaActualizada, resultado);
        verify(facturacionConsumer, times(1)).actualizarFactura(index, nuevaFactura);
    }

    @Test
    void testEliminarFactura() {
        // Arrange
        int index = 2;

        // Act
        controller.eliminarFactura(index);

        // Assert
        verify(facturacionConsumer, times(1)).eliminarFactura(index);
    }
}