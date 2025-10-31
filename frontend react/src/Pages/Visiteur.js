import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import '../PagesStyle/Visiteur.css';
import Footer from '../shared/Footer.js';
import logo from '../Images/logo.png';
import { useNavigate } from 'react-router-dom';

Modal.setAppElement('#root');

const Home = () => {
  const [offres, setOffres] = useState([]);
  const [isLoggedIn] = useState(false);
  const navigate = useNavigate();
  const [modalIsOpen, setModalIsOpen] = useState(false);


  useEffect(() => {
    fetchData();
  }, [isLoggedIn]);

  const fetchData = async () => {
    try {
      let response = await axios.get('http://localhost:8080/offre/getAllOffres');
      
      if (response) {
        setOffres(response.data);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const handleSignup = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  const handleRegisterS = () => {
    try {
      setTimeout(() => {
        navigate('/registerS');
      }, 100);
      closeModal();
    } catch (error) {
      console.error('Error removing items from localStorage:', error);
    }
  };

  const handleRegisterE  = () => {
    try {
      setTimeout(() => {
        navigate('/registerE');
      }, 100);
      closeModal();
    } catch (error) {
      console.error('Error removing items from localStorage:', error);
    }
  };
 
  const handleLogin = () => {
    try {
      setTimeout(() => {
        navigate('/login');
      }, 100);
    } catch (error) {
      console.error('Error removing items from localStorage:', error);
    }
  };

  const renderFormattedDate = (dateString) => {
    const currentDate = new Date();
    const postDate = new Date(dateString);
    const isSameYear = currentDate.getFullYear() === postDate.getFullYear();
    const timeDifference = currentDate - postDate;
    const hoursDifference = Math.floor(timeDifference / (1000 * 60 * 60));

    if (hoursDifference < 1) {
      return 'Just Now';
    } else if (hoursDifference < 24) {
      return `${hoursDifference} hours ago`;
    } else if (hoursDifference < 48) {
      return 'Yesterday';
    } else if (isSameYear) {
      const formattedDate = postDate.toLocaleDateString(undefined, { month: 'short', day: 'numeric' });
      return formattedDate;
    } else {
      const formattedDate = postDate.toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
      });
      return formattedDate;
    }
  };

  
  return (
    <div className="stage-connect-home-container">
      <nav className="stage-connect-navbar">
        <img src={logo} alt="StageConnect Logo" className="stage-connect-logo" />
        <div className="stage-connect-header">
          <div className="stage-connect-navbar-buttons">
              <React.Fragment>
                <p className='navi'>Welcome, Visiteur </p>
                <Modal isOpen={modalIsOpen} onRequestClose={closeModal} contentLabel="Choose Registration Type"
                        style={{
                            content: {
                              width: '500px', // Adjust the width as needed
                              height: '200px', // Adjust the height as needed
                              margin: 'auto',
                              backgroundColor: '#06263d', 
                            },
                          }} >
                    <h2>Choose Registration Type</h2><br/>
                    <button className={`animation ${isLoggedIn ? 'hidden' : ''}`} onClick={handleRegisterS} style={{ marginLeft: '40px', marginRight: '30px' }}>Stagiaire</button>
                    <button className={`animation ${isLoggedIn ? 'hidden' : ''}`} onClick={handleRegisterE} style={{ marginRight: '30px' }}>Entreprise</button>
                    <button className={`animation ${isLoggedIn ? 'hidden' : ''}`} onClick={closeModal}>Cancel</button>
                </Modal>
                <button className={`sign-animation login-animation ${isLoggedIn ? 'hidden' : ''}`} onClick={handleSignup} style={{ marginRight: '10px' }}>
                <nav></nav>
                <nav></nav>
                <nav></nav>
                <nav></nav>
                Signup</button>
                <button className={`login-animation ${isLoggedIn ? 'hidden' : ''}`} onClick={handleLogin}>
                <nav></nav>
                <nav></nav>
                <nav></nav>
                <nav></nav>Login</button>
              </React.Fragment>
          </div>
        </div>
      </nav>
  
      <div className="stage-connect-offres-list x" style={{ marginTop: '70px' }}>
       <div className="aside-left">
       </div>
       {Array.isArray(offres) && offres.map((offre) => (
          <div key={offre.id} className="stage-connect-offre-item">
            
  
            <div className="stage-connect-offre-content">
                 <React.Fragment> 
                 <div className="stage-connect-offre-header">
                 {offre.utilisateur && (
                     <img src={require(`C:/Users/haith/Desktop/image/${offre.utilisateur.image}`)} alt="User Icon" className="user-icon" />
                   )}
                   <div className="header-content">
                   <h2 style={{ marginBottom: '5px' }}>
                    <b>{localStorage.getItem('userNomE')}</b>
                    <b> {offre.utilisateur.stagiaire
                            ? `${offre.utilisateur.stagiaire.prenom} ${offre.utilisateur.stagiaire.nom}`
                          : (offre.utilisateur.entreprise && offre.utilisateur.entreprise.nom)}
                    </b> 
                  </h2>
                   <p className="date" >{renderFormattedDate(offre.date)}</p>
                   </div>
                 </div>
                 
                  <p><b>{offre.title}</b></p>
                 <p style={{ textAlign:"left"}}><b>Description:</b> {offre.description}</p>
                 <p style={{ marginLeft: '720px' }}><b>Address:</b> {offre.adr}</p>

                
                </React.Fragment>
              
            </div>
          </div>
        ))}
        <div className="aside-right">
          
        </div>
      </div>
      <Footer className="footer" />
    </div>
  );
};

export default Home;
