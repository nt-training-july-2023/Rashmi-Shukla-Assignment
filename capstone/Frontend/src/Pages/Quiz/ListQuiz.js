import React, { useEffect, useState } from "react";
import QuizService from "../../Services/QuizService";
import Swal from "sweetalert2";
import Navbar from "../../Components/Navbar/Navbar";
import "./Quiz.css";
import { useNavigate, useParams } from "react-router-dom";
import CategoryService from "../../Services/CategoryService";
import PageHeader from "../../Components/Header/PageHeader";
import QuizCard from "../../Components/Card/QuizCard/QuizCard";
import NoDataAvailable from "../../Components/NoDataAvailable/NoDataAvailable";

const ListQuiz = () => {
  const [quiz, setQuiz] = useState([]);
  const [categoryName, setCategoryName] = useState("");
  const navigate = useNavigate();
  const { id } = useParams();

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
    if (id) {
      getQuizzesByCategory();
      getCategoryName();
    } else {
      getQuizzes();
    }
  }, [id]);

  const getQuizzesByCategory = () => {
    CategoryService.getQuizzesByCategory(id)
      .then((response) => {
        setQuiz(response.data);
      })
      .catch((error) => {
        console.error(error)
      });
  };

  const getCategoryName = () => {
    CategoryService.getCategoryById(id)
      .then((response) => {
        setCategoryName(response.data.categoryTitle);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const getQuizzes = () => {
    QuizService.getQuizzes()
      .then((response) => {
        setQuiz(response.data);
      })
      .catch((error) => {
        console.error(error)
      });
  };

  const deleteQuiz = (quizId) => {
    QuizService.deleteQuiz(quizId)
      .then((response) => {
        Swal.fire({
          title: "Success",
          text: response.data.message,
          icon: "success",
          timer: 2000,
          showConfirmButton: false,
        });
        getQuizzes();
      })
      .catch((error) => {
        console.error(error)
      });
  };

  const heading = () => {
    if (id) {
      return `CATEGORY: ${categoryName}`;
    } else {
      return "QUIZZES";
    }
  };

  return (
    <div>
      <Navbar />
      <PageHeader
        className="quiz-header"
        heading={heading()}
        displayButton="true"
        onClick={() => navigate(`/quizzes/add`)}
        name="Add Quiz"
      />
      {quiz.length===0 ? (
        <NoDataAvailable />
      ):(
      <div className="quiz-container">
        {quiz.map((quizItem) => (
          <QuizCard key={quizItem.quizId} quizItem={quizItem} deleteQuiz={deleteQuiz}/>
        ))}
      </div>
      )}
    </div>
  );
};

export default ListQuiz;
