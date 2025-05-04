import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { FacturasComponent } from './pages/facturas/facturas.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'facturas', component: FacturasComponent },

];
