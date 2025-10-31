import React, { } from 'react'
import SocialIcons from './SocialIcon'; 
import './Footer.css'


const Footer = () => {
  

  const footerStyle = {
    position: 'fixed',
    width: '100%',
    bottom: '0',
    height: '65px',
    zIndex: 2,
  }
  return (
    <>
      <footer style={footerStyle} className={'py-4'} id={"footer"}>
        <div className='container-fluid'>
          <div className='row'>
            <div className='col-md-4'> 
            <p className='footer scrolling-text'>
                Sierra Bravo Intelligence &copy; {`${new Date().getFullYear()}`}
              </p><br/>
           </div>
          
            <div className='col-md-2 text-md-left'>
              <SocialIcons />
            </div>
          </div>
        </div>
      </footer>
    </>
  )
}

export default Footer
