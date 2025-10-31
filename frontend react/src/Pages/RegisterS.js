import { useState } from "react";
import axios from "axios";
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../PagesStyle/Register.css'; 


function RegistreS() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [selectedImage, setselectedImage] = useState(null);
    const [date_naiss, setDate_naiss] = useState("");
    const [signupError, setSignupError] = useState(false);

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
    
          await axios.post("http://localhost:8080/utilisateur/RegisterS", formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
    
          alert("Employee Registration Successfully");
        }
      } catch (err) {
        setSignupError(true);
        alert(err);
      }
    }
    
    return (
      <div className="sg">
      <div className="signup-container">
      <form className="form-signup">
        <ul className="signup-nav">
          <li className="signup-nav__item">
            <Link to="/">Sign In</Link>
          </li>
          <li className="signup-nav__item active">
              <a href>Sign Up</a>
          </li>
        </ul>
                  <input
                    type="text"
                    id="firstName"
                    placeholder="Enter First Name"
                    className={`form-control signup__input ${signupError ? 'is-invalid' : ''}`}
                    value={firstName}
                    onChange={(event) => setFirstName(event.target.value)}
                  />
                  <input
                    type="text"
                    id="lastName"
                    placeholder="Enter Last Name"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    value={lastName}
                    onChange={(event) => setLastName(event.target.value)}
                  />

                  <input
                    type="email"
                    id="email"
                    placeholder="Enter Email"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                  />

                  <input
                    type="password"
                    id="password"
                    placeholder="Enter Password"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                  />

                  <input
                    type="text"
                    id="date_naiss"
                    placeholder="Date_naissance"
                  className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    value={date_naiss}
                    onChange={(event) => setDate_naiss(event.target.value)}
                  />


                  <input
                    type="file"
                    id="image"
                    placeholder="Image"
                    className={`signup__input form-control ${signupError ? 'is-invalid' : ''}`}
                    onChange={(event) => setselectedImage(event.target.files[0])}
                  />

                
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
            </div></div>
    );
  }
  
  export default RegistreS;