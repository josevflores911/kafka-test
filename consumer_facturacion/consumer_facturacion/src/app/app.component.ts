import { Component, OnInit, OnDestroy } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FacturaService, Factura } from './factura.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule,HttpClientModule],
  providers:[FacturaService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
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
