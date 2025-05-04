import { Component, OnDestroy, OnInit } from '@angular/core';
import { Factura, FacturaService } from '../../factura.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HttpClientModule,FormsModule ],
  providers:[FacturaService],
  templateUrl: './facturas.component.html',
  styleUrl: './facturas.component.scss'
})
export class FacturasComponent implements OnInit, OnDestroy{
  title = 'consumer_facturacion';
  facturas: Factura[] = [];
  intervalId: any;

  modalAbierto = false;
  facturaSeleccionada!: Factura;
  facturaIndexSeleccionado!: number;

  constructor(private facturaService: FacturaService) {}

  ngOnInit(): void {
    this.cargarFacturas();
    this.intervalId = setInterval(() => this.cargarFacturas(), 5000); // polling cada 5 segundos
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  cargarFacturas(): void {
    this.facturaService.obtenerFacturas().subscribe(data => {
      this.facturas = data;
    });
  }

  

  abrirModal(factura: Factura, index: number): void {
    this.facturaSeleccionada = { ...factura }; // copia para edición
    this.facturaIndexSeleccionado = index;
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
  }

  confirmarActualizacion(): void {
    this.facturaService.actualizarFactura(this.facturaIndexSeleccionado, this.facturaSeleccionada)
      .subscribe({
        next: (facturaActualizada) => {
          this.facturas[this.facturaIndexSeleccionado] = facturaActualizada;
          this.modalAbierto = false;
        },
        error: (err) => {
          console.error('Error al actualizar factura:', err);
          alert('Error al actualizar la factura');
        }
      });
  }
  
  eliminarFactura(index: number): void {
    this.facturaService.eliminarFactura(index).subscribe({
      next: () => {
        this.facturas.splice(index, 1);
      },
      error: (err) => {
        console.error('Error al eliminar factura:', err);
        alert('Error al eliminar la factura');
      }
    });
  }
}
