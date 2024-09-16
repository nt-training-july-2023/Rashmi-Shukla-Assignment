import React, { useEffect } from 'react';
import { useState } from 'react';
import Swal from 'sweetalert2';
import { useNavigate } from 'react-router-dom'
import './Form.css';
import UserService from '../../Services/UserService';

const RegistrationForm = () => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState();
    const [email, setEmail ] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate(); 
    const userRole = localStorage.getItem("role");
    const IsLoggedIn = localStorage.getItem("IsLoggedIn");

    useEffect(() => {
      if (IsLoggedIn === "true" && userRole === "admin") {
        navigate("/dashboard");
      } else if (IsLoggedIn === "true" && userRole === "user") {
        navigate("/user-dashboard"); 
      }
    }, [IsLoggedIn,navigate,userRole]);
    
    const handleSubmit=async(e) =>{
      e.preventDefault()
      const validattionErrors ={}
      if(firstName === ""){
        validattionErrors.firstName = "*required"
      }
      if(email === ""){
        validattionErrors.email = '*required'
      }else if(!/^[a-zA-Z0-9.]+@nucleusteq\.com$/.test(email)){
        validattionErrors.email = "*email should end with @nucleusteq.com"
      }
      if(phoneNumber === ''){
        validattionErrors.phoneNumber = '*required'
      }else if(!/^([0-9]){10}$/.test(phoneNumber)){
        validattionErrors.phoneNumber = "*phone number should be of 10 digits"
      }
      if(password === ""){
        validattionErrors.password = "*required"
      }

      if(Object.keys(validattionErrors).length === 0){
        const user = { firstName, lastName, phoneNumber, email, password };
          UserService.register(user).then((response) => {
            Swal.fire({
              title: "Success",
              text: response.data.message,
              icon: "success",
              timer:2000,
              showConfirmButton:false
            });
            navigate("/")
          }).catch((error) => {
            if(error.response){
            const submitError=error.response.data.message
            Swal.fire({
              title: "Error",
              text: `${submitError}`,
              icon: "error",
              confirmButtonText: "Retry",
              confirmButtonColor:"red"
            });
          } else{
            Swal.fire({
              title:"Server not responding",
              icon: "error",
              confirmButtonText: "Retry Later",
              confirmButtonColor: "red",
            });
          }
          });
      }
      else{
        setErrors(validattionErrors)
      }
    }

    const redirectToLogin = () => { 
        navigate('/')
      }

  return (
    <div className='form-body'>
    <div className="Auth-form-container">
    <form className="Auth-form" onSubmit={handleSubmit}>
      <div className="Auth-form-content">
        <h3 className="Auth-form-title">Sign Up</h3>
        <div className="text-center">
          Already registered?{" "}
          <span className="redirect-link" style={{ fontSize:15}} onClick={redirectToLogin}>
            Login
          </span>
        </div>

        <div className="form-group">
          <label>First Name</label>
          <input
            type="text"
            className="form-input"
            value={firstName}
            onChange={(e) => {
              setFirstName(e.target.value);
              setErrors("");
            }}
          />
          {errors.firstName && <span>{errors.firstName}</span>}
        </div>

        <div className="form-group">
          <label>Last Name</label>
          <input
            type="text"
            className="form-input"
            value={lastName}
            onChange={(e) =>{
              setLastName(e.target.value);
              setErrors("");
            }}
          />
        </div>

        <div className="form-group">
          <label>Phone Number</label>
          <input
            type="number"
            className="form-input"
            value={phoneNumber || ''}
            onChange={(e) => {
              setPhoneNumber(e.target.value);
              setErrors("");
            }}
          />
          {errors.phoneNumber && <span>{errors.phoneNumber}</span>}
        </div>

        <div className="form-group">
          <label>Email address</label>
          <input
            type="email"
            className="form-input"
            value={email}
            onChange={(e) =>{
              setEmail(e.target.value);
              setErrors("");
            }}
          />
          {errors.email && <span>{errors.email}</span>}
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            className="form-input"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setErrors("");
            }}
          />
          {errors.password && <span>{errors.password}</span>}
        </div>
        <div className="button-group">
          <button type="submit"  className="button">
            Submit
          </button>
        </div>
        
      </div>
    </form>
  </div>
  </div>
  )
}

export default RegistrationForm
