import { useState } from "react";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, Link } from 'react-router-dom';
import { BsImage } from 'react-icons/bs';
import '../PagesStyle/Register.css'; 
import { FaFilePdf} from 'react-icons/fa';
import logo from '../Images/up.png';

function RegistreS() {
  
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [selectedImage, setSelectedImage] = useState(null);
    const [date_naiss, setDate_naiss] = useState("");
    const [cv, setCv] = useState(null);
    const [signupError, setSignupError] = useState(false);
    const navigate = useNavigate();
  
  
    async function save(event) {
      event.preventDefault();
      try {
        if (!firstName || !lastName || !email || !password || !date_naiss || !selectedImage) {
          console.log("Please fill in all fields");
          setSignupError(true);
        } else {
          const formData = new FormData();
          formData.append('nom', firstName);
          formData.append('prenom', lastName);
          formData.append('email', email);
          formData.append('password', password);
          formData.append('date_naissance', date_naiss);
          formData.append('image', selectedImage);
          formData.append('cv', cv);
              
          await axios.post("http://localhost:8080/utilisateur/RegisterS", formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
    
          navigate('/login');
        }
      } catch (err) {
        console.error("Error registering:", err);
        setSignupError(true);
        alert("Error registering: " + err.message);
        console.log(err.response);  // Add this line to log the entire response
    }
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
                    id="firstName"
                    placeholder="Enter First Name"
                    value={firstName}
                    style={{ marginTop: '-20px' }}
                    onChange={(event) => setFirstName(event.target.value)}
                  />

                  <input
                    type="text"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    id="lastName"
                    placeholder="Enter Last Name"
                    value={lastName}
                    onChange={(event) => setLastName(event.target.value)}
                  />

                  <input
                    type="email"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    id="email"
                    placeholder="Enter Email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                  />

                  <input
                    type="password"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    id="password"
                    placeholder="Enter Password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                  />

                  <input
                    type="date"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    id="date_naiss"
                    placeholder="Date_naissance"
                    value={date_naiss}
                    onChange={(event) => setDate_naiss(event.target.value)}
                  />

            <div className="input-group mb-3" style={{ marginTop: '5px' }}>
              <label className="input-group-text rounded-circle" htmlFor="cv">
                <FaFilePdf />
              </label>
              <input
                type="file"
                className={`form-control ${signupError ? 'is-invalid' : ''}`}
                id="cv"
                name="cv"
                onChange={(event) => setCv(event.target.files[0])}
              />
              
            </div>
                
            <div className="input-group mb-3" style={{ marginTop: '-10px' }}>
              <label className="input-group-text rounded-circle" htmlFor="image">
              <BsImage /></label>
              <input
                type="file"
                className={`form-control ${signupError ? 'is-invalid' : ''}`}
                id="image"
                name="image"
                onChange={(event) => setSelectedImage(event.target.files[0])}
              />
              
            </div>
                
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
       <img src={logo} alt="StageConnect sign" className="imgg"></img>
      </div></div>
      </div>
    );
  }
  
  export default RegistreS;