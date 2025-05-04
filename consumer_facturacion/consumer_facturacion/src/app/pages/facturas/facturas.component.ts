import { Component, OnDestroy, OnInit } from '@angular/core';
import { Factura, FacturaService } from '../../factura.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HttpClientModule],
  providers:[FacturaService],
  templateUrl: './facturas.component.html',
  styleUrl: './facturas.component.scss'
})
export class FacturasComponent implements OnInit, OnDestroy{
  title = 'consumer_facturacion';
  facturas: Factura[] = [];
  intervalId: any;

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
}
