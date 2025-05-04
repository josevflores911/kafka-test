

import React, { useState } from 'react';

function Pedidos() {
  const [mensaje, setMensaje] = useState('');

  const enviarPedido = async () => {
    try {
      const response = await fetch('http://localhost:8081/pedidos', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          productoId: 'abc123',
          cantidad: 5,
          clienteEmail: 'cliente@ejemplo.com',
        }),
      });

      if (!response.ok) {
        throw new Error('Error al enviar el pedido');
      }

      const data = await response.json();
      setMensaje(`Pedido enviado con éxito: ${JSON.stringify(data)}`);
    } catch (error) {
      setMensaje(`Error: ${error.message}`);
    }
  };

  return (
    <div>
      <h1>Crear Pedido</h1>
      <button onClick={enviarPedido}>Enviar Pedido</button>
      <p>{mensaje}</p>
    </div>
  );
}

export default Pedidos;
