import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Modal from 'react-modal';
import '../PagesStyle/Home.css';
import '../PagesStyle/update.css';
import Footer from '../shared/Footer.js';
import '../PagesStyle/Register.css';
import logo from '../Images/logo.png';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { faUserCheck } from '@fortawesome/free-solid-svg-icons';
import pdf from '../Images/pdf.png';
Modal.setAppElement('#root');

const PostE = () => {
  const [offres, setOffres] = useState([]);
  const [selectedOffre, setSelectedOffre] = useState(null);
  const [updatedTitle, setUpdatedTitle] = useState('');
  const [updatedDescription, setUpdatedDescription] = useState('');
  const [offreDate, setOffreDate] = useState('');
  const [offreAdr, setOffreAdr] = useState('');
  const [OffreId, setUserId] = useState('');
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [userRole, setUserRole] = useState('');
  const [isLoggedOut, setIsLoggedOut] = useState(false);
  const [offreTitle, setOffreTitle] = useState('');
  const [offreDescription, setOffreDescription] = useState('');
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [deleteOfferId, setDeleteOfferId] = useState(null);
  const [isDeleteConfirmationModalOpen, setIsDeleteConfirmationModalOpen] = useState(false);
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [crudError, setCrudError] = useState(false);
  const [userOffreId, setUserOffreId] = useState(null);
  const [offreId, setOffreId] = useState(null);
  const [postuleData, setPostuleData] = useState([]);
  const [isPostuleModalOpen, setIsPostuleModalOpen] = useState(false);
  const [posId, setposId] = useState(null);

  
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
  
  const fetchData = useCallback(async () => {
    try {
      if (!OffreId) {
        console.log('OffreId is undefined');
        return;
      }
  
      console.log('Fetching data for user with ID:', OffreId);
      let response;
      response = await axios.get(`http://localhost:8080/offre/user/${OffreId}`);
      if (response) {
        setOffres(response.data);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [OffreId]);
  

  useEffect(() => {
    const userRoleFromStorage = localStorage.getItem('userType');
    const userIdFromStorage = localStorage.getItem('userId');
    setUserRole(userRoleFromStorage);
    setUserId(parseInt(userIdFromStorage, 10));
  }, []);
  

  useEffect(() => {
    fetchData();
  }, [userRole, OffreId, isLoggedOut, fetchData, offres]);

  const handleCreateClick = () => {
    setOffreTitle('');
    setOffreDescription('');
    setOffreDate('');
    setOffreAdr('');

    setIsCreateModalOpen(true);
  };

  const handleCreateOffre = async (e) => {
    e.preventDefault();
    try {
        if (!offreTitle || !offreDescription || !offreDate || !offreAdr) {
            console.log("Please fill in all fields");
            setCrudError(true);
       } else { 
        const response = await axios.post(`http://localhost:8080/offre/createOffre/${OffreId}`, {
            title: offreTitle,
            description: offreDescription,
            date: offreDate,
            adr: offreAdr,
            utilisateur_id: OffreId,
        });

      console.log('Response:', response.data);

      if (response.status === 200) {
        console.log('Offre created', response.data);
        setOffreTitle('');
        setOffreDescription('');
        setOffreDate('');
        setOffreAdr('');
        fetchData();  
        
      }}setIsCreateModalOpen(false);
    } catch (error) {
      console.error('Error creating Offre:', error.message);
    }
  };
  

  const handleDelete = (id) => {
    setDeleteOfferId(id);
    setIsDeleteConfirmationModalOpen(true);
  };

  const confirmDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/offre/deleteOffre/${deleteOfferId}`);
      setIsDeleteConfirmationModalOpen(false);
      fetchData();
    } catch (error) {
      console.error('Error deleting offer:', error);
    }
  };
  

  const cancelDelete = () => {
    setDeleteOfferId(null);
    setIsDeleteConfirmationModalOpen(false);
  };
  

  const handleUpdateClick = (offre) => {
    setSelectedOffre(offre);
    setUpdatedTitle(offre.title);
    setUpdatedDescription(offre.description);
    setOffreDate(offre.date);
    setOffreAdr(offre.adr);
    setUserOffreId(offre.utilisateurId);
    setOffreId(offre.id);
  
    setIsUpdateModalOpen(true);
  };
  
  

  const handleUpdate = async () => {
    try {
      const response = await axios.put(
        `http://localhost:8080/offre/updateOffre/${selectedOffre.id}`,
        {
          title: updatedTitle,
          description: updatedDescription,
          date: offreDate,
          adr: offreAdr,
          utilisateurId: userOffreId,
          offreId: offreId,
        }
      );
  
      if (response.status === 200) {
        // Your existing logic here
  
        setIsUpdateModalOpen(false);
      } else {
        console.error('Error updating offer:', response.data);
      }
    } catch (error) {
      console.error('Error updating offer:', error);
    }
  };
  
  const handlepostule = async (id) => {
    try {
      setposId(id);
      const response = await axios.get(`http://localhost:8080/postuler/cvs/${id}`);
      if (response) {
        setPostuleData(response.data);
        setIsPostuleModalOpen(true);
      }
    } catch (error) {
      console.error('Error fetching postule data:', error);
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

  const handlehome = () => {
    try {
      setTimeout(() => {
        navigate('/home');
      }, 500);
    } catch (error) {
      console.error('fetchdata:', error);
    }
  };

  return (
    <div className="stage-connect-home-container">
    <div className=" fade-in">
      <nav className="stage-connect-navbar">
        <img src={logo} alt="StageConnect Logo" className="stage-connect-logo" />
        <div className="stage-connect-header">
          <div className="stage-connect-navbar-buttons">
            {userRole === 'Entreprise' && (
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
            )}
            {!userRole && (
              <Link to="/login" className="stage-connect-auth-button">
                Login
              </Link>
            )}
          </div>
        </div>
      </nav>


      <div className="stage-connect-offres-list x" style={{ marginTop: '-10px' }}> 
      <div className='xok' style={{ marginBottom: '20px' }}>
        <button className="stage-connect-create-button" onClick={handleCreateClick}>
        Create Offre
      </button><br/>
      </div>
      {Array.isArray(offres) && offres.map((offre) => (
          <div>
          <div key={offre.id} className="stage-connect-offre-item" style={{ marginBottom: '10px' }}>
            <div className="stage-connect-offre-content">
              {userRole === 'Entreprise' && (
                <React.Fragment> 
                  <div className="stage-connect-offre-header">
                  {offre.utilisateur && (
                      <img src={require(`C:/Users/haith/Desktop/image/${offre.utilisateur.image}`)} alt="User Icon" className="user-icon" />
                    )}
                    <div className="header-content">
                    <h2>
                      <b>
                        {offre.utilisateur.stagiaire
                          ? `${offre.utilisateur.stagiaire.prenom} ${offre.utilisateur.stagiaire.nom}`
                          : (offre.utilisateur.entreprise && offre.utilisateur.entreprise.nom)}
                      </b> </h2>
                      <p className="datee">{renderFormattedDate(offre.date)}</p>
                    </div>
                  </div>
                   <p><b>{offre.title}</b></p>
                   <p style={{ textAlign:"left"}}><b>Description:</b> {offre.description}</p>
                 <p style={{ marginLeft: '720px' }}><b>Address:</b> {offre.adr}</p>
                  <div className="stage-connect-crud-buttons">
                    <button onClick={() => handleDelete(offre.id)} className="stage-connect-crud-button">
                      Delete
                    </button>
                    <button onClick={() => handleUpdateClick(offre)} className="stage-connect-crud-button">
                      Update
                    </button>
                    <div style={{ backgroundColor: "#03e8f47d", padding: "5px", borderRadius: "40%", cursor: "pointer" }} onClick={() => handlepostule(offre.id)}>
                      <FontAwesomeIcon icon={faUserCheck} style={{ color: "white" }} />
                    </div>

                  </div>
                </React.Fragment>
              )}
            </div>
            </div><div className="postul"> <p></p></div>
          </div>
        ))}
      </div>

      <Modal
        className={'stage-connect-update-modal'}
        isOpen={isDeleteConfirmationModalOpen}
        onRequestClose={cancelDelete}
        contentLabel="Delete Confirmation Modal"
        style={{
          overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
          },
          content: {
            width: '30%',
            height: '180px',
            background: '#06263d',
            boxSizing: 'border-box',
            color: '#fff',
          },
        }}
      >
        <div className="xx">
        <div className="stage-connect-update-form">
          <h5>Confirm Deletion</h5>
          <b className='bb'>Are you sure you want to delete this offer?</b>
          <div className="modal-buttons" style={{ marginTop: '20px' }}>
          
          <button onClick={confirmDelete} className="stage-connect-crud-button" style={{ marginRight: '10px' }}>
              Confirm
            </button>
            <button onClick={cancelDelete} className="stage-connect-crud-button" style={{ marginLeft: '10px' }}>
              Cancel
            </button></div>
          </div>
        </div>
      </Modal>

      <Modal
  className={'stage-connect-update-modal'}
  isOpen={isUpdateModalOpen || isCreateModalOpen}
  onRequestClose={() => {
    setIsUpdateModalOpen(false);
    setIsCreateModalOpen(false);
  }}
  contentLabel={isCreateModalOpen ? "Create Offre Modal" : "Update Offre Modal"}
  style={{
    overlay: {
      backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    content: {
      width: '60%',
      maxWidth: '800px',
      height: '80%',
      maxHeight: '350px',
      background: 'rgba(0, 0, 100, 0.561)',
      boxSizing: 'border-box',
      color: '#fff',
    },
  }}
>
  <div className={`stage-connect-update-form ${isUpdateModalOpen || isCreateModalOpen ? 'modal-open' : ''}`}>

  {isCreateModalOpen && (
  <div className="loginBox">
    <div className="inputBox">
      <div style={{ display: 'flex', flexDirection: 'row', gap: '15px',marginTop: '80px' }}>
        <div style={{ flex: 1}}>
          <input
            type="text"
            placeholder="Title"
            className={`signup__input form-control ${crudError ? 'is-invalid' : ''} white-placeholder`}
            value={offreTitle}
            onChange={(e) => setOffreTitle(e.target.value)}
            required
          />
        </div>
        <div style={{ flex: 1 }}>
          <input
            type="date"
            placeholder="Date"
            value={offreDate}
            className={`signup__input form-control ${crudError ? 'is-invalid' : ''} white-placeholder`}
            onChange={(e) => setOffreDate(e.target.value)}
            required
          />
        </div>
        <div style={{ flex: 1 }}>
          <input
            type="text"
            placeholder="Address"
            value={offreAdr}
            className={`signup__input form-control ${crudError ? 'is-invalid' : ''} white-placeholder`}
            onChange={(e) => setOffreAdr(e.target.value)}
            required
          />
        </div>
      </div>

      <textarea
        placeholder="Description"
        value={offreDescription}
        className={`signup__input form-control ${crudError ? 'is-invalid' : ''} white-placeholder`}
        onChange={(e) => setOffreDescription(e.target.value)}
        required
      />

      <button type="submit" onClick={handleCreateOffre} style={{ marginLeft: "70px", marginTop: "10px" }}>Create Offre</button>
      <br />
      {crudError && (
        <p className="error-message" style={{ color: "red" }}>Please fill in all fields!</p>
      )}
    </div>
  </div>
)}


    {isUpdateModalOpen && (
      <div className="loginBox">
        <div className="inputBox">
        <div style={{ display: 'flex', flexDirection: 'row', gap: '15px',marginTop: '80px' }}>
        <div style={{ flex: 1 }}>
          <input
            type="text"
            placeholder="Title"
            value={updatedTitle}
            required
            onChange={(e) => setUpdatedTitle(e.target.value)}
          />
        </div>
        <div style={{ flex: 1 }}>
          <input
            type="date"
            placeholder="Date"
            value={offreDate}
            onChange={(e) => setOffreDate(e.target.value)}
          />
        </div>
        <div style={{ flex: 1 }}>
          <input
            type="text"
            placeholder="Address"
            value={offreAdr}
            onChange={(e) => setOffreAdr(e.target.value)}
          />
        </div>
      </div>

          <textarea
            placeholder="Description"
            value={updatedDescription}
            onChange={(e) => setUpdatedDescription(e.target.value)}
          />

          <button onClick={handleUpdate} className="stage-connect-crud-button">
            Update
          </button>
          <button onClick={() => setIsUpdateModalOpen(false)} className="stage-connect-crud-button">
            Cancel
          </button>
        </div>
      </div>
    )}
  </div>
</Modal>

<Modal
        className={'postule-modal'}
        isOpen={isPostuleModalOpen}
        onRequestClose={() => setIsPostuleModalOpen(false)}
        contentLabel="Postule Modal"
        style={{
          overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
          },
          content: {
            width: '20%',
            maxWidth: '8200px',
            height: '80%',
            maxHeight: '350px',
            background: '#1c2e769e',
            boxSizing: 'border-box',
            color: '#fff',
            marginLeft:"1160px",
            marginTop:"220px"
          },
        }}
      >
  <div className="postule-modal-content"><br/>
  <h2 className='ue'>CVs submitted for Offre ID: {posId}</h2>
    <ul>
      {postuleData.map((fileName, index) => (
        <li key={index}>
          <p> <a href={`/pdfs/${fileName}`} target="_blank" rel="noopener noreferrer">
                    <img src={pdf} alt="arz" style={{ width: "50px", height: "50px", marginLeft:"-170px"}}/></a></p>
                    
        </li>
      ))}
    </ul>
        </div>
      </Modal>
      <Footer />
    </div></div>
  );
};

export default PostE;
