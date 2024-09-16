import React, { useEffect, useState } from "react";
import "./Navbar.css";
import Swal from "sweetalert2";
import logo from '../../Assests/images/logo.png';
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  const [valid, setValid] = useState("");
    const result = localStorage.getItem("role");

    useEffect(() => {
        if (result === "admin") {
      setValid("true");
        } else {
      setValid("false");
    }},[result]);

  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  const handleLogoutConfirmation =() =>{
    Swal.fire({
      text:"Confirm Logout?",
      icon:"warning",
      showCancelButton:true,
      showConfirmButton:true,
      confirmButtonText:"Confirm"
    }).then((result)=>{
      if(result.isConfirmed){
        handleLogout();
      }
      });
  }

  const handleLogout = () =>{
    navigate('/')
    Swal.fire({
      text: "You've successfully logged out!",
      icon: "success",
      timer: 2000,
      showConfirmButton:false
    });
    localStorage.removeItem("IsLoggedIn")
    localStorage.removeItem("role")
    localStorage.removeItem("userName");
    localStorage.removeItem("userEmail");
  }

  return (
    <div>
      <nav className="navbar">
        <button onClick={toggleMenu} className="hamburger-btn">
          &#9776;
        </button>
        <div className={`navbar-container ${menuOpen ? 'show':''}`}>
           <img src ={logo} alt="logo" className="logo"/>
          {(valid==="true") ? (<Link to="/dashboard" className="navbar-item">
            Home
          </Link>): (<Link to="/user-dashboard" className="navbar-item">
            Home
          </Link>)}

          <Link to="/categories" className="navbar-item">
            Category
          </Link>
          <Link to="/quizzes" className="navbar-item">
            Quizzes
          </Link> 
          <Link to="/results" className="navbar-item">
            Result
          </Link>
          
          <Link to='#' className="navbar-item"><button onClick={handleLogoutConfirmation} className="nav-button"><span style={{color:"white"}}
          class="material-symbols-outlined">
            Logout
          </span> </button></Link>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
