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
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';
import { faStar as regularStar } from '@fortawesome/free-regular-svg-icons';
import { faUser } from '@fortawesome/free-solid-svg-icons';

Modal.setAppElement('#root');

const Home = () => {
  const [offres, setOffres] = useState([]);
  const [OffreId, setUserId] = useState('');
  const [userRole, setUserRole] = useState('');
  const [isLoggedOut, setIsLoggedOut] = useState(false);
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [emailSent] = useState(false);
  const [favoriteOffers, setFavoriteOffers] = useState([]);
  const [showFavoritesModal, setShowFavoritesModal] = useState(false);


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

  const handlePostuler = async (offerId) => {
    try {
      const idstagaire = localStorage.getItem('idstagaire');
      const userId = localStorage.getItem('userId');
      console.log(userId)
      const Response = await axios.get(`http://localhost:8080/utilisateur/cv/${idstagaire}`);
      const cvData = Response.data;

      await axios.post(`http://localhost:8080/postuler/apply/${offerId}/${userId}/${cvData}`);

  } catch (error) {
    console.error('Error submitting application:', error);
  }
};

  /*const [appliedInternEmail, setAppliedInternEmail] = useState('');
  const isEmail = (email) =>
 /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email);

  useEffect(() => {
    if (offres && offres.length > 0) {
      const firstOffre = offres[0];
      axios.get(`http://localhost:8080/utilisateur/email/${firstOffre.id}`)
        .then(response => {
          if (response.data && response.data.email) {
            console.log('Fetched email:', response.data.email);
            setAppliedInternEmail(response.data.email);
          } else {
            console.error('Invalid response format. Missing email property:', response.data);
          }
        })
        .catch(error => {
          console.error('Error fetching applied intern email:', error);
        });

        const postulationId = response.data; 
        console.log('Postulation ID:', postulationId);


    } catch (error) {
        console.error('Error submitting application:', error);
    }
   }, [offres]);
   */
  

  
   const handleSendEmail = async (offre) => {
    try {
      const sendEmailResponse = await axios.post(`http://localhost:8080/email/send/${offre.utilisateur.email}`);
      console.log('Email sent successfully:', sendEmailResponse);
      alert('Email sent successfully');
      fetchData();
    } catch (error) {
      console.error('Error sending email:', error);
  
      
      console.error('Error details:', error.response?.data || error.message);
  
      alert('An error occurred while sending the email');
    }
  };
  
  
  const handledelete = async (offre) => {
    try {
      const response = await axios.delete(`http://localhost:8080/postuler/delete/${offre.post.id}/${offre.utilisateur.id}`);
      console.log(offre.id);
      console.log(offre.utilisateur.id);
      console.log('Postulation deleted successfully:', response.data);
      fetchData();
    } catch (error) {
      console.error('Error deleting postulation:', error);
      
      console.error('Error details:', error.response?.data || error.message);
      
      alert('An error occurred while deleting the postulation');
    }
  };
  
  
  const fetchData = useCallback(async () => {
    try {
      let response;
  
      if (userRole === 'Stagiaire') {
        response = await axios.get('http://localhost:8080/offre/getAllEntrepriseOffres');
      } else if (userRole === 'Entreprise') {
        const userId = localStorage.getItem('userId');
        response = await axios.get(`http://localhost:8080/utilisateur/entrepriseId/${userId}`);
        const idrep = response.data;
        response = await axios.get(`http://localhost:8080/postuler/entreprise/${idrep}`);
  
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

  const handlePostes = () => {
    try {
      setTimeout(() => {
        navigate('/postS');
      }, 500);
    } catch (error) {
      console.error('fetchdata:', error);
    }
  };
/////////////////////////////////////////
useEffect(() => {
  const fetchOffres = async () => {
    try {
      const response = await axios.get('http://localhost:8080/offre/getAllOffres');
      setOffres(response.data);
    } catch (error) {
      console.error('Error fetching offres:', error);
    }
  };

  const fetchFavorites = async () => {
    try {
      const userId = localStorage.getItem('idstagaire');
      console.log('id for user',userId)
      const response = await axios.get(`http://localhost:8080/favorites/user/${userId}/favorites`);
      //setFavoriteOffers(response.data);
      console.log('Favorite offers6:', response.data);
  
      
      // if (Array.isArray(response.data) && response.data.length > 0) {
      //   setFavoriteOffers(response.data.offers); 
      // } 
      if (Array.isArray(response.data) && response.data.length > 0) {
        // Assuming the response.data structure resembles the provided one
        const favoriteOffersData = response.data.map(item => ({
          offre: item.offre,
          id:item.id // Assuming 'offre' holds the offer details
        }));
  
        setFavoriteOffers(favoriteOffersData);
      }
      else {
        console.error('Invalid data structure for favorite offers');
      }
    } catch (error) {
      console.error('Error fetching favorites:', error);
    }
  };

  fetchOffres();
  fetchFavorites();
}, []);

const isFavorite = (offreId) => {
  //return Array.isArray(favoriteOffers) && favoriteOffers.some((favorite) => favorite.offre.id === offreId);
  return (
    Array.isArray(favoriteOffers) &&
    favoriteOffers.some((favorite) => favorite.offre && favorite.offre.id === offreId)
  );
};

const handleFavorite = async (offreId) => {
  try {
    const userId = localStorage.getItem('idstagaire');
  await axios.post('http://localhost:8080/favorites/addFavorite', null, {
    params: {
      stagiaireId: userId,
      offreId: offreId,
    },
  });
  const response = await axios.get(`http://localhost:8080/favorites/user/${userId}/favorites`);
  const favoriteOffersData = response.data.map(item => ({
    offre: item.offre,
    id: item.id,
  }));

  setFavoriteOffers(favoriteOffersData); // Update the state with the new data
} catch (error) {
  console.error('Error adding favorite:', error);
}
  //   setFavoriteOffers([...favoriteOffers, { stagiaireId: userId, offreId }]);
  // } catch (error) {
  //   console.error('Error adding favorite:', error);
  // }
  
};   
console.log('favorite offers4:',favoriteOffers)

const handleShowFavorites = () => {
  if (showFavoritesModal===false){
  setShowFavoritesModal(true);}
  else{setShowFavoritesModal(false);}
};

const handleListe = () => {
  try {
    setTimeout(() => {
      navigate('/List');
    }, 500);
  } catch (error) {
    console.error('fetchdata:', error);
  }
};

const handleCloseModal = () => {
  setShowFavoritesModal(false);
};


const handleDeleteFavorite = async (favoriteId) => {
  try {
    console.log('favoriteId', favoriteId)
    // Make a DELETE request to your API endpoint to delete the favorite by ID
    await axios.delete(`http://localhost:8080/favorites/${favoriteId}`);
    
    // Remove the deleted favorite from the state
    const updatedFavorites = favoriteOffers.filter(favorite => favorite.id !== favoriteId);
    setFavoriteOffers(updatedFavorites);
  } catch (error) {
    console.error('Error deleting favorite:', error);
  }
};


const Star = ({ isFavorite, onClick }) => {
  return (
    <button className={`star-icon ${isFavorite ? 'active' : ''}`} onClick={onClick}>
      <FontAwesomeIcon icon={isFavorite ? solidStar : regularStar} />
    </button>
  );
};

const handleProfile = () => {
  try {
    setTimeout(() => {
  navigate('/profil');
}, 500);
} catch (error) {
console.error('fetchdata:', error);
}
}; 
const handleProfile2 = () => {
  try {
    setTimeout(() => {
  navigate('/profilE');
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
            {userRole === 'Stagiaire' && (
              <React.Fragment>
                <p className='nav' style={{marginRight: "400px"}} >Welcome, {localStorage.getItem('userPrenom')} {localStorage.getItem('userNom')} </p>
                
                <button
                className={`acc-animation`}
                onClick={handleShowFavorites}
                style={{height:"40px"}}>
                Show Favorites</button><div className="dropdown">
              <button
                className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                onClick={() => setIsDropdownOpen(!isDropdownOpen)}>
                <FontAwesomeIcon icon={faUser} />
              </button>
              {isDropdownOpen && (
                <div className="dropdown-menu">
                  <button
                    className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                    onClick={handleProfile} >
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    Account
                  </button>
                  <button
                    className={`pst-animation ${isLoggedOut ? 'hidden' : ''}`}
                    onClick={handlePostes}>
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
            )}
            {userRole === 'Entreprise' && (
              <React.Fragment>
                <p className='nav' style={{marginRight: "480px"}} >Welcome, {localStorage.getItem('userNomE')}</p>
                <button
                className={`acc-animation`}
                onClick={handleListe}
                style={{height:"40px"}}>
                Liste d'attente</button>
                <div className="dropdown">
              <button
                className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                onClick={() => setIsDropdownOpen(!isDropdownOpen)}>
                <FontAwesomeIcon icon={faUser} />
              </button>
              {isDropdownOpen && (
                <div className="dropdown-menu">
                  <button
                    className={`acc-animation ${isLoggedOut ? 'hidden' : ''}`}
                    onClick={handleProfile2}>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    <nav></nav>
                    Account
                  </button>
                  <button
                    className={`pst-animation ${isLoggedOut ? 'hidden' : ''}`}
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
            )}
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
              {userRole === 'Entreprise' && (
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
                  <div className="stage-connect-crud-buttons">
                  <button className="post" onClick={() => handleSendEmail(offre)}>
                 
                    {emailSent ? 'Email Sent!' : 'Send Email'}
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                  </button>
                  <button className="post" onClick={() => handledelete(offre)}>
                  <span></span>
                <span></span>
                <span></span>
                <span></span>
                      Delete Postulation
                      
                    </button>
               
                </div>
                </React.Fragment>
              )}
  
              {(userRole === 'Stagiaire' && offre.utilisateur.entreprise) && (
                    <React.Fragment> 
                  <button
                      className="star-icon"
                      style={{ border: 'none', background: 'none', padding: 0 }}
                      onClick={() => handleFavorite(offre.id)}
                    >
                      <Star
                        isFavorite={isFavorite(offre.id)}
                        offreId={offre.id}
                        userId={localStorage.getItem('userId')}
                      />
                    </button>

                    {offre.utilisateur && (
                   <div className="stage-connect-offre-header">
                   {offre.utilisateur && (
                       <img src={require(`C:/Users/haith/Desktop/image/${offre.utilisateur.image}`)} alt="User Icon" className="user-icon" />
                     )}
                     <div className="header-content">
                     <h2><b>
                            {offre.utilisateur
                            ? `${localStorage.getItem('userNomE')}`
                            : (offre.utilisateur.role === 'Entreprise' && offre.utilisateur.entreprise && offre.utilisateur.entreprise.nom)}
                    </b>
                    <b>
                          {offre.utilisateur.stagiaire
                            ? `${offre.utilisateur.stagiaire.prenom} ${offre.utilisateur.stagiaire.nom}`
                            : (offre.utilisateur.entreprise && offre.utilisateur.entreprise.nom)}
                    </b> </h2>
                     <p style={{ marginRight: '40px' }} className="date" >{renderFormattedDate(offre.date)}</p>
                     </div>
                   </div>
                    )}
                   
                    <p><b>{offre.title}</b></p>
                    <p style={{ textAlign:"left"}}><b>Description:</b> {offre.description}</p>
                   <p style={{ marginLeft: '600px' }}><b>Address:</b> {offre.adr}</p>
                   <div className="stage-connect-crud-buttons">
                  <button className=" post" onClick={() => handlePostuler(offre.id)}>
                  <span></span>
                  <span></span>
                  <span></span>
                  <span></span>
                  Postuler</button>
                  </div>
                  </React.Fragment>
                  
                )}


          {favoriteOffers && (
          <Modal
              isOpen={showFavoritesModal}
              onRequestClose={handleCloseModal}
              contentLabel="Favorite Offers Modal"
              className="favorite-offers-modal"
              overlayClassName="modal-overlay"
              style={{
                overlay: {
                  backgroundColor: 'rgba(0, 0, 0, 0.5)',
                },
                content: {
                  width: '27%',
                  maxWidth: '8200px',
                  height: '80%',
                  maxHeight: '450px',
                  background: '#1c2e769e',
                  boxSizing: 'border-box',
                  color: '#fff',
                  marginLeft:"1160px",
                  marginTop:"150px"
                },
              }}
           >
        <div className="favorite-offers-content"> 
        
          <h3 style={{color:"white"}}>Your Favorite Offers</h3>
          <hr style={{marginTop:"-10px", marginBottom:"-22px"}}/>
          <div className="favorite-offers-list">
            {console.log('favorite offers2:',favoriteOffers)}
            {favoriteOffers.map((favorite, index) => {
              console.log('favorite.id:',favorite.id)
              const offre = favorite.offre;
              if (!offre || !offre.id) {
                
                return (
                  <div key={index}>
                    Missing or Invalid Offer Data
                   
                  </div>
                );
              }
    

              return (
              <div key={favorite.offre.id} className="stage-connect-offres-list favorite-offer-item" style={{ marginLeft:"20px", width: '320px', height:'350px' ,marginBottom: '-15px' }}>
            {favorite.offre && favorite.offre.utilisateur && (
              <React.Fragment> 
                 <div className="stage-connect-offre-header">
                 {offre.utilisateur && (
                     <img src={require(`C:/Users/haith/Desktop/image/${offre.utilisateur.image}`)} alt="User Icon" className="user-icon" />
                   )}
                   <div className="header-content">
                   {favorite.offre.utilisateur && favorite.offre.utilisateur.entreprise && (
                   <b>{favorite.offre.utilisateur.entreprise.nom}</b>
                   )}<br/>
                   <b style={{ marginRight: '40px', color:"white" }} className="date" >{renderFormattedDate(offre.date)}</b>
                   </div>
                 </div>
                 
                  <p><b>{favorite.offre.title}</b></p>
                  <p style={{marginLeft:"-60px",marginTop:'-20px'}}><b>Description:</b> {favorite.offre.description}</p>
                  <p style={{marginLeft:"-80px", marginTop:'-20px'}}><b>Address:</b> {favorite.offre.adr}</p>
                 <div className="stage-connect-crud-buttons">
                <button className=" post" onClick={() => handlePostuler(favorite.offre.id)}>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                Postuler</button>
                {console.log('favorite',)}
                <button className=" post" onClick={() => handleDeleteFavorite(favorite.id)}>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                Delete</button>
                </div>
                </React.Fragment>
            )}
            </div>
              );
            })}
           
          </div>
        </div>
         </Modal>
)}
            </div>
          </div>
        ))}
      </div>

      <Footer className="footer" />
    </div></div>
  );
};

export default Home;