import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Factura {
  productoId: string;
  cantidad: number;
  clienteEmail: string;
  total: number;
}

@Injectable({
  providedIn: 'root',
})
export class FacturaService {
  private apiUrl = 'http://localhost:8090/facturas';

  constructor(private http: HttpClient) {}

  obtenerFacturas(): Observable<Factura[]> {
    return this.http.get<Factura[]>(this.apiUrl);
  }

  actualizarFactura(index: number, factura: Factura): Observable<Factura> {
    return this.http.put<Factura>(
      `http://localhost:8090/facturas/${index}`,
      factura
    );
  }

  eliminarFactura(index: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8090/facturas/${index}`);
  }
}
