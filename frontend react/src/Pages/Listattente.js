import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Modal from 'react-modal';
import '../PagesStyle/Home.css';
import '../PagesStyle/update.css';
import Footer from '../shared/Footer.js';
import logo from '../Images/logo.png';
import pdf from '../Images/pdf.png';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';

Modal.setAppElement('#root');

const Home = () => {
  const [offres, setOffres] = useState([]);
  const [OffreId, setUserId] = useState('');
  const [userRole, setUserRole] = useState('');
  const [isLoggedOut, setIsLoggedOut] = useState(false);
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const fetchData = useCallback(async () => {
    try {
      let response;
  
      if (userRole === 'Stagiaire') {
        response = await axios.get('http://localhost:8080/offre/getAllEntrepriseOffres');
      } else if (userRole === 'Entreprise') {
        const userId = localStorage.getItem('userId');
        response = await axios.get(`http://localhost:8080/utilisateur/entrepriseId/${userId}`);
        const idrep = response.data;
        response = await axios.get(`http://localhost:8080/postuler/entrepriset/${idrep}`);
  
        const resp = response.data;
        console.log(resp);
  
      }

      if (response) {
        setOffres(response.data);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [userRole]);
  

  useEffect(() => {
    const userRoleFromStorage = localStorage.getItem('userType');
    const userIdFromStorage = localStorage.getItem('userId');
    setUserRole(userRoleFromStorage);
    setUserId(parseInt(userIdFromStorage, 10));
  }, []);
  
  useEffect(() => {
    fetchData();
  }, [userRole, OffreId, isLoggedOut, fetchData]);

  const handleLogout = () => {
    try {
      localStorage.removeItem('userEmail');
      localStorage.removeItem('userId');
      localStorage.removeItem('userPrenom');
      localStorage.removeItem('userNom');
      localStorage.removeItem('userNomE');
      setIsLoggedOut(true);

      setTimeout(() => {
        navigate('/');
      }, 500);
    } catch (error) {
      console.error('Error removing items from localStorage:', error);
    }
  };

  const handlePostee = () => {
    try {
      setTimeout(() => {
        navigate('/postE');
      }, 500);
    } catch (error) {
      console.error('fetchdata:', error);
    }
  };

const handlehome = () => {
  try {
    setTimeout(() => {
      navigate('/home');
    }, 500);
  } catch (error) {
    console.error('fetchdata:', error);
  }
};




////////////////////////////////
  return (
    <div className="stage-connect-home-container">
    <div className=" fade-in">
      <nav className="stage-connect-navbar">
        <img src={logo} alt="StageConnect Logo" className="stage-connect-logo" />
        <div className="stage-connect-header">
          <div className="stage-connect-navbar-buttons">
              <React.Fragment>
                <p className='nav' style={{marginRight: "480px"}}>Liste d'attente pour les stagiaires acceptés</p>
                <div className="dropdown">
              <button
                className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                onClick={() => setIsDropdownOpen(!isDropdownOpen)}>
                <FontAwesomeIcon icon={faUser} />
              </button>
              {isDropdownOpen && (
                <div className="dropdown-menu">
                <button
                className={`hm-animation`}
                onClick={handlehome}
                style={{width:"122px"}}>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                Home</button>
                  <button
                    className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                    onClick={() => console.log('View Account')}>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    Account
                  </button>
                  <button
                    className={`pst-animation ${isLoggedOut ? 'hidden' : ''}`}
                    style={{width:"122px"}}
                    onClick={handlePostee}>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    Posts
                  </button>
                  <button
                    className={`logout-animation btnlog ${isLoggedOut ? 'hidden' : ''}`}
                    onClick={handleLogout}>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    Logout
                  </button>
                </div>
              )}
            </div>
              </React.Fragment>
            
            {!userRole && (
              <Link to="/login" className="stage-connect-auth-button">
                Login
              </Link>
            )}
          </div>
        </div>
      </nav>
  
      <div className="stage-connect-offres-list x" style={{ marginTop: '70px' }}>
    {Array.isArray(offres) && offres.map((offre) => (
    <div key={offre.id} className="stage-connect-offre-item" style={{ marginBottom: '10px' }}>
            <div className="stage-connect-offre-content">
                <React.Fragment> 
                  <div className="stage-connect-offre-header">
                  {offre.utilisateur && (
                      <img src={require(`C:/Users/haith/Desktop/image//${offre.utilisateur.image}`)} alt="User Icon" className="user-icon" />
                    )}
                    <div className="header-content">
                    <h2>
                  <b>
                        {offre.utilisateur.stagiaire
                          ? `${offre.utilisateur.stagiaire.prenom} ${offre.utilisateur.stagiaire.nom}`
                          : (offre.utilisateur.entreprise && offre.utilisateur.entreprise.nom)}
                  </b> </h2>
                      
                    </div>
                  </div>
                  <p><b>Stagiaire Email: <br/> {offre.utilisateur.email}</b></p>
                  <p>
                    <b>
                      <a href={`/pdfs/${offre.cv}`} target="_blank" rel="noopener noreferrer">
                        <img src={pdf} alt="cv" style={{ width: "50px", height: "50px" }} />
                      </a>
                    </b>
                  </p>
                  <br />
                </React.Fragment>
              
            </div>
          </div>
        ))}
      </div>

      <Footer className="footer" />
    </div></div>
  );
};

export default Home;