import React, { useState, useEffect } from "react";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import "./Form.css";
import UserService from "../../Services/UserService";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
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

  const handleLoginClick = async (event) => {
    event.preventDefault();

    const validattionErrors = {};
    if (email === "") {
      validattionErrors.email = "*required";
    }
    if (password === "") {
      validattionErrors.password = "*required";
    }

    if (Object.keys(validattionErrors).length === 0) {
      const user = {email,password}
      UserService.login(user).then((response) => {
        if(response.data.role === "user"){
          navigate("/user-dashboard");
        } else if(response.data.role === "admin") {
          navigate("/dashboard");
        }
        localStorage.setItem("IsLoggedIn", true);
        localStorage.setItem("role", response.data.role);
        localStorage.setItem("userName", response.data.fullName)
        localStorage.setItem("userEmail", response.data.email)
      }).catch((error) => {
        if(error.response){
        const submitError = error.response.data.message;
        Swal.fire({
          title: "Error",
          text: `${submitError}`,
          icon: "error",
          confirmButtonText: "Retry",
          confirmButtonColor: "red",
        });
      } else {
        Swal.fire({
          title:"Server not responding",
          icon: "error",
          confirmButtonText: "Retry Later",
          confirmButtonColor: "red",
        });
      }
        setEmail("");
        setPassword("");
      })
    } else {
      setErrors(validattionErrors);
    }
  };

  const redirectToRegister = () => {
    navigate("/register");
  };

  return (
    <div className="form-body">
      <div className="content-container">
        <h1>ASSESSMENT PORTAL</h1>
        <p className="large-p">"Assess, Excel, Succeed"</p>
        <button onClick={redirectToRegister}>Register Now</button>
      </div>
      <div className="Auth-form-container">
        <form className="Auth-form" onSubmit={handleLoginClick}>
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Login</h3>
            <div className="form-group">
              <label>Email address</label>
              <input
                type="email"
                className="form-input"
                value={email}
                onChange={(e) => {
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
                onChange={(e) =>{
                  setPassword(e.target.value);
                  setErrors("");
                }}
              />
              {errors.password && <span>{errors.password}</span>}
            </div>

            <div className="button-group">
              <button type="submit" className="button">
                Login
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
