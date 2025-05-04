import './styles/component.css'
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Pedidos from './pages/Pedidos';
import Footer from './components/Footer';
import MainContent from './components/MainContent';

function App() {
  return (
    <Router>
    <div className="layout">
      <Header />
      <div className="content-wrapper">
        <Sidebar />
        {/* <MainContent /> */}
        <main className="main-content">
            <Routes>
              <Route path="/" element={<Home />} />

              <Route path="/pedidos" element={<Pedidos />} />
            </Routes>
          </main>
      </div>
      <Footer />
    </div>

    </Router>
  );
}

export default App;

