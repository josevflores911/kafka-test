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
  providedIn: 'root'
})
export class FacturaService {
  private apiUrl = 'http://localhost:8090/facturas';

  constructor(private http: HttpClient) {}

  obtenerFacturas(): Observable<Factura[]> {
    return this.http.get<Factura[]>(this.apiUrl);
  }
}
