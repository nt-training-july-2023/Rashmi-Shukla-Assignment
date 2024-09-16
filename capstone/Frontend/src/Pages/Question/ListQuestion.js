import React, { useEffect, useState } from "react";
import QuestionService from "../../Services/QuestionService";
import { useNavigate, useParams } from "react-router-dom";
import Navbar from "../../Components/Navbar/Navbar";
import "./Question.css";
import QuizService from "../../Services/QuizService";
import Swal from "sweetalert2";
import ResultService from "../../Services/ResultService";
import PageHeader from "../../Components/Header/PageHeader";
import DisableBack from "../../Components/DisableBack";
import NoDataAvailable from "../../Components/NoDataAvailable/NoDataAvailable";

const ListQuestion = () => {
  const [questions, setQuestions] = useState([]);
  const [numberOfQuestions, setNumberOfQuestions] = useState(0);
  const currentDate = new Date();
  const dateTime = `${currentDate.getDate()}-${
    currentDate.getMonth() + 1
  }-${currentDate.getFullYear()} ${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
  localStorage.setItem("dateTime",dateTime)

  const [totalMarks, setTotalMarks] = useState(0);
  const [totalQuestions, setTotalQuestions] = useState(0);
  const [quizTitle, setQuizTitle] = useState([]);
  const [categoryTitle, setCategoryTitle] = useState("");

  const [attemptedQuestions, setAttemptedQuestions] = useState(0);
  const [obtainedMarks, setObtainedMarks] = useState(0);
  const [submitted, setSubmitted] = useState(false);
  const [selectedAnswers, setSelectedAnswers] = useState({});

  const navigate = useNavigate();
  const userRole = localStorage.getItem("role");
  const { id } = useParams();

  const [timeInSeconds, setTimeInSeconds] = useState(0);
  const [loadingQuestions, setLoadingQuestions] = useState(true);

  useEffect(() => {
    if(userRole==="user"){

    const prevCount = parseInt(localStorage.getItem('reloadAttempts')) || 0;
    const newCount = prevCount + 1;
    localStorage.setItem('reloadAttempts', newCount.toString());

    if (localStorage.getItem('reloadAttempts') >= 3 && localStorage.getItem('reloadAttempts') <= 6) {
        Swal.fire({
            icon:"error",
            title: 'Refreshing the page will submit the quiz!',
            confirmButtonText: 'Continue',
            showCancelButton: 'Cancel'
        }).then((result) => {
            if (result.isConfirmed) {
                handleSubmit();
            }
        });
    } else if (localStorage.getItem('reloadAttempts') > 6) {
         handleSubmit();
    }
}}, [userRole]);

  useEffect(() => {
    const storedSelectedAnswers = localStorage.getItem("selectedAnswers");
    if(storedSelectedAnswers) {
      setSelectedAnswers(JSON.parse(storedSelectedAnswers));
    }

    const storedTimer = localStorage.getItem("timerInSeconds");
    if (storedTimer) {
      const timerInSeconds = parseInt(storedTimer);
      setTimeInSeconds(timerInSeconds);
    }
      getQuestionsByQuiz();
  }, []);
  
  useEffect(() => {
    if(userRole === "user"){
    const handleCountdown = () => {
      if (timeInSeconds > 0) {
        setTimeInSeconds((prevTime) => prevTime - 1);
        localStorage.setItem("timerInSeconds", (timeInSeconds - 1).toString());
      } else if(numberOfQuestions>0){
        handleSubmit();
      }
    };
    const countdownInterval = setInterval(handleCountdown, 1000);
    // Clean up the interval when the component unmounts
    return () => clearInterval(countdownInterval);
  }}, [timeInSeconds,userRole,numberOfQuestions]);

  // Format the remaining time as HH:MM:SS
  const formattedTime = new Date(timeInSeconds * 1000)
    .toISOString()
    .substr(11, 8);

  const getQuestionsByQuiz = () => {
    setLoadingQuestions(true);
    const storedTimer = localStorage.getItem("timerInSeconds");
    QuizService.getQuestionsByQuiz(id)
      .then((response) => {
        if(response.data.length>0){
          setQuestions(response.data);
          setNumberOfQuestions(response.data.length);
          setTotalQuestions(response.data.length);
          localStorage.setItem("totalQuestions",response.data.length)
          setTotalMarks(response.data.length);
          localStorage.setItem("totalMarks",response.data.length)
          setQuizTitle(response.data[0].quiz.quizTitle);
          localStorage.setItem("quizTitle",response.data[0].quiz.quizTitle)
          setCategoryTitle(response.data[0].quiz.category.categoryTitle);
          localStorage.setItem("categoryTitle",response.data[0].quiz.category.categoryTitle)
          const timerInMinutes = response.data[0].quiz.quizTimer;
          const timerInSeconds = timerInMinutes * 60;
          if (!storedTimer) {
            setTimeInSeconds(timerInSeconds);
            userRole==="user" && localStorage.setItem("timerInSeconds",
              timerInSeconds.toString());
          }
      }
        setLoadingQuestions(false);
      })
      .catch((error) => {
        setLoadingQuestions(false);
        console.error(error);
      });
  };

  const deleteQuestion = (questionId) => {
    QuestionService.deleteQuestion(questionId)
      .then((response) => {
        Swal.fire({
          title: "Success",
          text: response.data.message,
          icon: "success",
          timer: 2000,
          showConfirmButton: false,
        });

        getQuestionsByQuiz();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleOptionChange = (questionId, selectedOption, correctAnswer) => {

    if (!submitted) {
      setSelectedAnswers((prevSelectedAnswers) => {
        const updatedSelectedAnswers = {
          ...prevSelectedAnswers,
          [questionId]: selectedOption,
        };
  
        if (userRole === "user") {
          localStorage.setItem("selectedAnswers", JSON.stringify(updatedSelectedAnswers));
        }
  
        calculateMarks(updatedSelectedAnswers);
  
        return updatedSelectedAnswers;
      });
    }

  };

  const calculateMarks = (updatedSelectedAnswers) => {
    let score = 0;
    for (const question of questions) {
      const questionId = question.questionId;
      if (updatedSelectedAnswers[questionId] === question.answer) {
        score += 1;
      }
    }
  
    setObtainedMarks(score);
    localStorage.setItem("obtainedMarks", score);
    setAttemptedQuestions(Object.keys(updatedSelectedAnswers).length);
    localStorage.setItem("attemptedQuestions", Object.keys(updatedSelectedAnswers).length);
  };

  const handleSubmit = (e) => {
    if (e) {
      e.preventDefault();
    }
    setSubmitted(true);
   
    const result = {
      totalMarks: localStorage.getItem("totalMarks"),
      obtainedMarks: localStorage.getItem("obtainedMarks"),
      attemptedQuestions: localStorage.getItem("attemptedQuestions"),
      totalQuestions: localStorage.getItem("totalMarks"),
      dateTime: localStorage.getItem("dateTime"),
      userEmail: localStorage.getItem("userEmail"),
      userName: localStorage.getItem("userName"),
      quizTitle: localStorage.getItem("quizTitle"),
      categoryTitle: localStorage.getItem("categoryTitle")
    };

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
  
    ResultService.addResult(result).then((response) => {
      Swal.fire({
        title: "Success",
        text: "Quiz submitted successfully",
        icon: "success",
        timer: 2000,
        showConfirmButton: false,
      });
    });
    navigate("/user-dashboard");
    
  };

  return (
    <div>
      {userRole === "user" && numberOfQuestions>0 &&<DisableBack/>}
      {userRole === "admin" && <Navbar />}
      <PageHeader
        className="question-header"
        heading={`QUIZ: ${quizTitle}`}
        displayButton={userRole === "admin"}
        name="Add Question"
        isTest={userRole === "user"}
        timer={formattedTime}
        onClick={
          userRole === "admin"
            ? () => navigate(`/quizzes/${id}/questions/add`)
            : handleSubmit
        }
      />
      {questions.length===0 ? (
        <NoDataAvailable />
      ):(
      <div className="question-container">
        {questions.map((question, index) => (
          <div key={index} className="question-card">
            <div className="question-form">
              <h5 className="question-card-title">
                Q{++index} : {question.questionTitle}
              </h5>

              {Object.values(question.options).map(
                (optionValue, optionIndex) => (
                  <p key={optionIndex} className="question-card-text">
                    <input
                      type="radio"
                      name={`options-${question.questionId}`}
                      value={optionValue}
                      onChange={() =>
                        handleOptionChange(
                          question.questionId,
                          optionValue,
                          question.answer
                        )
                      }
                      checked={
                        selectedAnswers[question.questionId] === optionValue
                      }
                      disabled={submitted}
                    />
                    {optionValue}
                  </p>
                )
              )}

              <br />
              {userRole === "admin" && (
                <p className="question-card-text">
                  Correct answer: {question.answer}
                </p>
              )}
            </div>
            {userRole === "admin" && (
              <div className="question-btns">
                <button
                  className="ques-update-btn"
                  onClick={() =>
                    navigate(
                      `/quizzes/${id}/questions/update/${question.questionId}`
                    )
                  }
                >
                  Update
                </button>
                <button
                  className="ques-delete-btn"
                  onClick={() =>
                    Swal.fire({
                      title: "Warning",
                      text: "Delete Question?",
                      icon: "warning",
                      confirmButtonText: "Delete",
                      confirmButtonColor: "red",
                      showCancelButton: true,
                    }).then((result) => {
                      if (result.isConfirmed) {
                        deleteQuestion(question.questionId);
                      }
                    })
                  }
                >
                  Delete
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
      )}
    </div>
  );
};

export default ListQuestion;
