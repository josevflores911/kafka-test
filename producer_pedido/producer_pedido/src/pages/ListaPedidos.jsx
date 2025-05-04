import React, { useState } from 'react';
import '../styles/pedidos.css'

const productos = [
  { id: 'abc123', nombre: 'Producto 1', precio: 100.00, imagen: 'https://via.placeholder.com/150' },
  { id: 'def456', nombre: 'Producto 2', precio: 120.00, imagen: 'https://via.placeholder.com/150' },
  { id: 'ghi789', nombre: 'Producto 3', precio: 89.99, imagen: 'https://via.placeholder.com/150' },
];

function ListaPedidos() {
  const [mensaje, setMensaje] = useState('');

  const enviarPedido = async (producto) => {
    try {
      const response = await fetch('http://localhost:8081/pedidos', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          productoId: producto.id,
          cantidad: 1,
          clienteEmail: 'cliente@ejemplo.com',//from session
          precio: producto.valor 
        }),
      });

      if (!response.ok) {
        throw new Error('Error al enviar el pedido');
      }

      const data = await response.json();
      setMensaje(`✅ Pedido enviado: ${JSON.stringify(data)}`);
    } catch (error) {
      setMensaje(`❌ Error: ${error.message}`);
    }
  };

  return (
    <div>
      <h1>Opciones de Compra</h1>
      <div className="product-container">
        {productos.map((prod) => (
          <div className="product-card" key={prod.id}>
            <img src={prod.imagen} alt={prod.nombre} />
            <h3>{prod.nombre}</h3>
            <p className="price">${prod.precio.toFixed(2)}</p>
                <button onClick={() => enviarPedido({ id:prod.id,valor:prod.precio} )}>Comprar</button>
          </div>
        ))}
      </div>
      <p>{mensaje}</p>
    </div>
  );
}

export default ListaPedidos;
