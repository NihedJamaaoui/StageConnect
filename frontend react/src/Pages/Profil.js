import React, { useState, useEffect } from 'react';
import axios from 'axios'; 
import '../PagesStyle/profil.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Footer from '../shared/Footer.js';
import logo from '../Images/logo.png';
import { useNavigate } from 'react-router-dom';
import { faUser } from '@fortawesome/free-solid-svg-icons';

const UpdateProfile = () => {
  const [nom, setNom] = useState('');
  const [email, setEmail] = useState('');
  const [prenom, setPrenom] = useState('');
  const [dateNaissance, setDateNaissance] = useState('');
  const [cv, setCv] = useState('');
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isLoggedOut, setIsLoggedOut] = useState(false);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const storedUserId = localStorage.getItem('userId');
        const response = await axios.get(`http://localhost:8080/utilisateur/getById/${storedUserId}`);
console.log('data',response.data);
  

setEmail(response.data.email)
          setNom(response.data.stagiaire.nom);
          setPrenom(response.data.stagiaire.prenom);
          setDateNaissance(response.data.stagiaire.date_naissance);
          setCv(response.data.stagiaire.cv);
        
      } 
      catch (error) {
        console.error("Error while fetching user data:", error.message);
      }
    };
    fetchUserData();
  }, []); 

  const handleUpdate = async () => {
    try {
      const userData = {
        nom,
        email,
        prenom,
        dateNaissance,
        cv,
      
      
      };
      console.log("userdata",userData);
      const storedUserId1 = localStorage.getItem('userId');
      console.log("id", storedUserId1)

        const response = await axios.put(`http://localhost:8080/utilisateur/update/${storedUserId1}`,
          {
            email: email,
            stagiaire:{nom: userData.nom,
            prenom: userData.prenom,
            cv: userData.cv,
            date_naissance: userData.dateNaissance,}
      });
  
      if (response.status === 200) {
        
        
        alert('Update successful');
      } else {
        console.error('Server response not successful:', response.status);
        
      }
    
    } catch (error) {
    
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

  return (
    <div className="stage-connect-navbar2">
    <div className=" fade-in">
      <nav className="stage-connect-navbar">
      <img src={logo} alt="StageConnect Logo" className="stage-connect-logo" />
      <div className="stage-connect-header">
        <div className="stage-connect-navbar-buttons">
          
            <React.Fragment>
              <p className='nav'>This is your pofil, {localStorage.getItem('userNomE')}</p>
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
                onClick={() => console.log('View Account')}>
                  <nav></nav>
                  <nav></nav>
                  <nav></nav>
                  <nav></nav>
                  Account
                </button>
                <button
                  className={`pst-animation ${isLoggedOut ? 'hidden' : ''}`}
                  onClick={handlehome}>
                  <nav></nav>
                  <nav></nav>
                  <nav></nav>
                  <nav></nav>
                  Home
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
        
          
  
          
        </div>
      </div>
    </nav>
    <div className="centered-container">
      
       <input type="text" value={nom} onChange={(e) => setNom(e.target.value)} className={`signup__input form-control 'is-invalid' : ''}`}/> 
      <input type="text" value={prenom} onChange={(e) => setPrenom(e.target.value)} className={`signup__input form-control 'is-invalid' : ''}`}/> 
       <input type="text" value={dateNaissance} onChange={(e) => setDateNaissance(e.target.value)}className={`signup__input form-control 'is-invalid' : ''}`} /> 
       <input type="text" value={email} onChange={(e) => setEmail(e.target.value)} className={`signup__input form-control 'is-invalid' : ''}`}/> 
      {/* <input type="file" value={cv} onChange={(e) => setCv(e.target.value)} className={`signup__input form-control 'is-invalid' : ''}`}/>  */}
      <button className="btn btn-primary w-50 update__submit" onClick={handleUpdate} >Update</button>
      </div>
    <Footer />
    </div>
    </div>
  );
};

export default UpdateProfile;
