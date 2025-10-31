import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BsImage } from 'react-icons/bs';
import '../PagesStyle/Register.css'; 
import logo from '../Images/up.png';

function RegisterEntreprise() {
  const [nom, setNom] = useState("");
  const [adresse, setAdresse] = useState("");
  const [telephone, setTelephone] = useState("");
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [selectedImage, setSelectedImage] = useState(null);
  const [role] = useState("");
  const [signupError, setSignupError] = useState(false);
  const navigate = useNavigate();

  async function save(event) {
    event.preventDefault();
    try {
      if (!nom || !adresse || !telephone || !mail || !password || !selectedImage) {
        console.log("Please fill in all fields");
        setSignupError(true);
      } else {
        const formData = new FormData();
        formData.append("nom", nom);
        formData.append("adresse", adresse);
        formData.append("telephone", telephone);
        formData.append("email", mail);
        formData.append("password", password);
        formData.append("image", selectedImage);
  
        await axios.post("http://localhost:8080/utilisateur/RegisterE", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        navigate('/login');
      }
    } catch (err) {
      alert(err.message);
    }
    console.log(nom, adresse, telephone, mail, password, selectedImage, role);
  }
  

  return (
        <div className="sg">
          <div className=" fade-in">
        <div className="signup-container">
        <form className="form-signup"> 
          <ul className="signup-nav">
            <li className="signup-nav__item">
              <Link to="/login">Sign In</Link>
            </li>
            <li className="signup-nav__item active">
                <a href>Sign Up</a>
            </li>
          </ul>
                <input
                  type="text"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                  id="nom"
                  placeholder="Enter Nom de l'entreprise"
                  value={nom}
                  onChange={(event) => setNom(event.target.value)}
                />
                <input
                  type="text"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                  id="adresse"
                  placeholder="Enter Adresse"
                  value={adresse}
                  onChange={(event) => setAdresse(event.target.value)}
                />

                <input
                  type="text"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                  id="telephone"
                  placeholder="Enter Téléphone"
                  value={telephone}
                  onChange={(event) => setTelephone(event.target.value)}
                />
              

                <input
                  type="email"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                  id="mail"
                  placeholder="Enter Email"
                  value={mail}
                  onChange={(event) => setMail(event.target.value)}
                />
              
                <input
                  type="password"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                  id="password"
                  placeholder="Enter Mot de passe"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                />
             
             <div className="input-group mb-3" style={{ marginTop: '5px' }}>
              <label className="input-group-text rounded-circle" htmlFor="image">
              <BsImage /></label>
              <input
                type="file"
                className={`form-control ${signupError ? 'is-invalid' : ''}`}
                id="image"
                name="image"
                onChange={(event) => setSelectedImage(event.target.files[0])}
              /></div>

              
              <button type="submit" className="btn btn-primary w-100 signup__submit" onClick={save}>
              <span></span>
                <span></span>
                <span></span>
                <span></span>
                  Save
                </button><br/>
                 {signupError && (
                   <p className="error-message">Please fill in all fields!</p>
                  )}
              </form>
              <a href="/" className="signup__forgot">
                 DÉJA INSCRIT?
              </a>
            </div>
            <div className="signn-container">
       <img src={logo}  alt="StageConnect sign" className="imgg"></img>
      </div>
            </div></div>
  );
}

export default RegisterEntreprise;
