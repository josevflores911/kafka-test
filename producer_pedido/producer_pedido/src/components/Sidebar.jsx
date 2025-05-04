import { Link } from 'react-router-dom';

function Sidebar() {
  return (
    <aside className="sidebar">
      <nav>
        <ul>
          <li><Link to="/">Inicio</Link></li>
          {/* <li><Link to="/facturas">Facturas</Link></li> */}
          {/* <li><Link to="/pedidos">Pedidos</Link></li> */}
          <li><Link to="/listapedidos">Pedidos</Link></li>
        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;
