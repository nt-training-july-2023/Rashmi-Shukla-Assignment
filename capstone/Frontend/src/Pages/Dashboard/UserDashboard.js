import React, { useEffect } from "react";
import Navbar from "../../Components/Navbar/Navbar";
import welcomeImg from "../../Assests/images/welcome-img.png";
import ErrorPage from "../ErrorPage/ErrorPage";
import { useNavigate } from "react-router-dom";
import PageHeader from "../../Components/Header/PageHeader";
import progressImg from "../../Assests/images/growth.png";
import DisableBack from "../../Components/DisableBack";
import Button from "../../Components/Button/Button";

const UserDashboard = () => {
  const userName = localStorage.getItem("userName");
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem("timerInSeconds");
    localStorage.removeItem("selectedAnswers");
    localStorage.removeItem("quizTitle");
    localStorage.removeItem("categoryTitle");
    localStorage.removeItem("totalQuestions");	
    localStorage.removeItem("totalMarks");	
    localStorage.removeItem("reloadAttempts");
    localStorage.removeItem("obtainedMarks");
    localStorage.removeItem("attemptedQuestions");
    localStorage.removeItem("dateTime");
  }, []);

  const userRole = localStorage.getItem("role");
  if (userRole !== "user") {
    return <ErrorPage />;
  }

  return (
    <div className="dashboard-container">
      <DisableBack/>
      <Navbar />
      <PageHeader
        className="dashboard-header"
        heading="USER DASHBOARD"
        displayButton=""
        onClick=""
        name=""
      />
      <div className="dashboard-content">
        <div className="welcome-box">
          <h1>Welcome, {userName}</h1>
          <div>
            <img src={welcomeImg} alt="img" />
          </div>
        </div>
        <div className="user-profile-box">
          <div>
          <img className="growth-img" src={progressImg} alt="progress icon"></img>
          <div/>
          <div>
            <Button className="custom-button" onClick={() => navigate("/results")} name ="Check Your Progress"/>
          </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserDashboard;
