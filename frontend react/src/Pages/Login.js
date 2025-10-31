import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../PagesStyle/Login.css';
import Modal from 'react-modal';
import logo from '../Images/signn.png';
import { jwtDecode } from 'jwt-decode';
import '../PagesStyle/Home.css';
import '../PagesStyle/update.css';
import Footer from '../shared/Footer.js';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import Star from './Star';
import { FaAlignCenter } from 'react-icons/fa';




Modal.setAppElement('#root');

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loginError, setLoginError] = useState(false);
  const [keepLoggedIn, setKeepLoggedIn] = useState(false); 
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/utilisateur/Login', {
        email: email,
        password: password,
      });
  
      if (response.status === 204) {
        console.log("User doesn't exist!");
        setLoginError(true);
      } else if (response.status === 200) {
        const token = response.data.token;
        const decodedToken = jwtDecode(token);
  
        
        const userEmail = decodedToken.sub; 
  
        // Fetch user information using the email
        const userResponse = await axios.get(`http://localhost:8080/utilisateur/getUserByEmail?email=${userEmail}`);
        const user = userResponse.data;
  
        localStorage.setItem('userType', user.entreprise ? 'Entreprise' : 'Stagiaire');
        localStorage.setItem('userId', user ? user.id : '');

        localStorage.setItem('userNom', user.stagiaire ? user.stagiaire.nom : '');
        localStorage.setItem('userPrenom', user.stagiaire ? user.stagiaire.prenom : '');
        localStorage.setItem('idstagaire', user.stagiaire ? user.stagiaire.id : '');


        localStorage.setItem('userNomE', user.entreprise ? user.entreprise.nom : '');
  
        navigate('/home');
      } else {
        setLoginError(true);
        console.error('Login failed. Response data:', response.data);
      }
    } catch (error) {
      setLoginError(true);
      console.error('Error during login:', error.message);
    }
  };
  

  const handleSignupModal = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  return (
    <div className="ln">
      <div className=" fade-in">
    <div className="login-container">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      <form onSubmit={handleLogin} className="form-login">
        <ul className="login-nav">
          <li className="login-nav__item active">
            <a href>Sign In</a>
          </li>
          <li className="login-nav__item">
          <Link to="#" onClick={handleSignupModal} className="login__signup">
            Sign Up
          </Link>
          </li>
        </ul>

        <label htmlFor="email" className="login__label">
          Email
        </label>
        <input
          type="email"
          id="email"
          placeholder="Email"
          className={`login__input form-control ${loginError ? 'is-invalid' : ''}`}
          value={email}
          onChange={(e) => {
            setEmail(e.target.value);
            setLoginError(false);
          }}
        />

        <label htmlFor="password" className="login__label">
          Password
        </label>
        <input
          type="password"
          id="password"
          placeholder="Password"
          className={`login__input form-control ${loginError ? 'is-invalid' : ''}`}
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
            setLoginError(false);
          }}
        />

        <label htmlFor="keepLoggedIn" className="login__label--checkbox">
          <input
            id="keepLoggedIn"
            type="checkbox"
            className="login__input--checkbox"
            checked={keepLoggedIn}
            onChange={() => setKeepLoggedIn(!keepLoggedIn)}
          />
          Keep me Signed in
        </label>

        <button type="submit" className="btn btn-primary w-100 login__submit">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
          Login
        </button><br/><br/>
         {loginError && (
        <p className="error-message" >Incorrect username or password.</p>
      )}
      </form>
      <a href className="login__forgot">
        Forgot Password?
      </a>
      <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            contentLabel="Choose Signup Type"
            style={{
              content: {
                width: '400px', // Adjust the width as needed
                height: '160px', // Adjust the height as needed
                margin: 'auto',
                backgroundColor: '#06263d', // Set the background color
              },
            }}
          >
            <h2 style={{ color: '#ffffff' }}>Choose Signup Type</h2><br/>
            
            <Link to="/registerS" onClick={closeModal} className={`animation`} style={{ marginLeft: '20px', marginRight: '20px' }}>
              Stagiaire
            </Link>
            <Link to="/registerE" onClick={closeModal}className={`animation`} style={{ marginRight: '20px' }}>
              Entreprise
            </Link>
            <Link className={`animation`} onClick={closeModal}>Cancel</Link>
          </Modal>
        </div>
      
      <div className="loginn-container">
       <img src={logo} alt="StageConnect sign" className='imgg' ></img>
      </div>
      </div>
    </div>
    
  );
};

export default Login;