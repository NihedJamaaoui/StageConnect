import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Register from './Authentification/Register';
import RegisterEntreprise from './Authentification/RegisterEntreprise';
import Login from './Pages/Login';
import Home from './Pages/Home';
import Visiteur from './Pages/Visiteur';
import PostE from './Pages/PostE';
import PostS from './Pages/PostS';
import Listattente from './Pages/Listattente';
import Profil from './Pages/Profil';
import ProfilE from './Pages/ProfilE';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/registerS" element={<Register />} />
          <Route path="/registerE" element={<RegisterEntreprise />} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Visiteur />} />
          <Route path="/PostE" element={<PostE />} />
          <Route path="/PostS" element={<PostS />} />
          <Route path="/List" element={<Listattente />} />
          <Route path="/Profil" element={<Profil />} />
          <Route path="/ProfilE" element={<ProfilE />} /> 

          {/* <:!!<Route path="/Profil" element={<Profil />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
