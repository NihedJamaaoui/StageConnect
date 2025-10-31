import React, { Fragment } from 'react'
import { FaFacebook, FaTwitter, FaLinkedin,FaInstagram } from 'react-icons/fa'

const socialMediaStyle = {
  color: '#fec632',
  width: '2rem',
  float: 'right'
}

const SocialIcons = () => {
  return (
    <Fragment>
      <span className='d-inline'>
        <a
          href='https://twitter.com/i/flow/login?redirect_after_login=%2FSierraBravoInt1'
          target='_blank'
          rel='noreferrer'
        >
          <FaTwitter style={socialMediaStyle} fontSize='20px' />
        </a>
      </span>
      <span className='d-inline'>
        <a
          href='https://www.linkedin.com/company/sierra-bravo-intelligence/?originalSubdomain=fr'
          target='_blank'
          rel='noreferrer'
        >
          <FaLinkedin style={socialMediaStyle} fontSize='20px' />
        </a>
      </span>
      <span >
        <a
          href='https://www.facebook.com/profile.php?id=100087609177194'
          target='_blank'
          rel='noreferrer'
        >
          <FaFacebook style={socialMediaStyle} fontSize='20px' />
          
        </a>
      </span>  
      <span>
        <a
          href='https://www.instagram.com/sierra_bravo_intelligence_/'
          target='_blank'
          rel='noreferrer'
        >
          <FaInstagram style={socialMediaStyle} fontSize='20px' />
        </a>
      </span>    
    </Fragment>
  )
}

export default SocialIcons
