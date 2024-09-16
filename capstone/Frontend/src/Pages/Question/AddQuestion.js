import React, { useEffect, useState } from "react";
import QuizService from "../../Services/QuizService";
import QuestionService from "../../Services/QuestionService";
import { Link, useNavigate, useParams } from "react-router-dom";
import Swal from "sweetalert2";
import Navbar from "../../Components/Navbar/Navbar";
import ErrorPage from "../ErrorPage/ErrorPage";

const AddQuestion = () => {
  const [questionTitle, setQuestionTitle] = useState("");
  const [optionI, setOptionI] = useState("");
  const [optionII, setOptionII] = useState("");
  const [optionIII, setOptionIII] = useState("");
  const [optionIV, setOptionIV] = useState("");
  const [answer, setAnswer] = useState("");
  const [quiz, setQuiz] = useState(null);
  const [errors, setErrors] = useState("");
  const navigate = useNavigate();
  const { id } = useParams(); 
  const { quizId, questionId } = useParams(); 

  useEffect(() => {
    if (id) {
      getQuizById();
    }
  }, [id]);

  const getQuizById = () => {
    QuizService.getQuizById(id)
      .then((reponse) => {
        setQuiz(reponse.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const validateForm = () => {
    if (
      questionTitle === "" ||
      optionI === "" ||
      optionII === "" ||
      optionIII === "" ||
      optionIV === "" ||
      answer === ""
    ) {
      setErrors("*All the fields are mandatory");
      return true;
    }
    if (!areOptionsUnique()) {
      setErrors("*Options must be unique");
      return true;
    }
    return false;
  };

  const areOptionsUnique = () => {
    const optionsArray = [optionI, optionII, optionIII, optionIV];
    const uniqueOptions = new Set(
      optionsArray.map((option) => option.toLowerCase())
    );
    return optionsArray.length === uniqueOptions.size;
  };

  const handleCorrectOptionChange = (e) => {
    setAnswer(e.target.value);
    setErrors("");
  };

  const saveQuestion = (e) => {
    e.preventDefault();
    if (!validateForm()) {
      const options = { optionI, optionII, optionIII, optionIV };
      const question = { questionTitle, options, answer, quiz };
      if (questionId) {
        QuestionService.updateQuestion(questionId, question)
          .then((response) => {
            Swal.fire({
              title: "Success",
              text: response.data.message,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            });
            navigate(`/quizzes/${quizId}/questions`);
          })
          .catch((error) => {
            const submitError = error.response.data.message;
            Swal.fire({
              title: "Error",
              text: `${submitError}`,
              icon: "error",
              confirmButtonText: "Retry",
              confirmButtonColor: "red",
            });
          });
      } else {
        QuestionService.addQuestion(question)
          .then((response) => {
            Swal.fire({
              title: "Success",
              text: response.data.message,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            });
            navigate(`/quizzes/${id}/questions`);
          })
          .catch((error) => {
            const submitError = error.response.data.message;
            Swal.fire({
              title: "Error",
              text: `${submitError}`,
              icon: "error",
              confirmButtonText: "Retry",
              confirmButtonColor: "red",
            });
          });
      }
    }
  };

  const heading = () => {
    if (questionId) {
      return <h2 style={{ textAlign: "center" }}>UPDATE QUESTION</h2>;
    } else {
      return <h2 style={{ textAlign: "center" }}>ADD QUESTION</h2>;
    }
  };

  useEffect(() => {
    if (questionId) {
      QuestionService.getQuestionById(questionId)
        .then((response) => {
          setQuestionTitle(response.data.questionTitle);
          setOptionI(response.data.options.optionI);
          setOptionII(response.data.options.optionII);
          setOptionIII(response.data.options.optionIII);
          setOptionIV(response.data.options.optionIV);
          setAnswer(response.data.answer);
          setQuiz(response.data.quiz);
        })
        .catch((error) => {
        });
    }
  }, [questionId]);

  const userRole = localStorage.getItem("role");
  if (userRole !== "admin") {
    return <ErrorPage />;
  }

  return (
    <div className="page-container">
      <Navbar />
      <div className="cat-container">
        <form className="cat-form">
          <div>{heading()}</div>
          <label>Question Text</label>

          <input
            type="text"
            value={questionTitle}
            onChange={(e) => {
              setQuestionTitle(e.target.value);
              setErrors("");
            }}
          />

          <label>Option 1</label>
          <input
            type="text"
            value={optionI}
            onChange={(e) => {
              setOptionI(e.target.value);
              setErrors("");
            }}
          />

          <label>Option 2</label>
          <input
            type="text"
            value={optionII}
            onChange={(e) => {
              setOptionII(e.target.value);
              setErrors("");
            }}
          />

          <label>Option 3</label>
          <input
            type="text"
            value={optionIII}
            onChange={(e) => {
              setOptionIII(e.target.value);
              setErrors("");
            }}
          />

          <label>Option 4</label>
          <input
            type="text"
            value={optionIV}
            onChange={(e) => {
              setOptionIV(e.target.value);
              setErrors("");
            }}
          />

          <label>Correct Answer</label>
          <select
            name=""
            id="category-select"
            value={answer}
            onChange={handleCorrectOptionChange}
          >
            <option value="">Select a Correct Option</option>
            <option value={optionI}>{optionI}</option>
            <option value={optionII}>{optionII}</option>
            <option value={optionIII}>{optionIII}</option>
            <option value={optionIV}>{optionIV}</option>
          </select>

          <label>Quiz</label>
          <input
            type="text"
            value={quiz ? quiz.quizTitle : "not available"}
            readOnly
            disabled
          />

          <span>{errors}</span>

          <div>
            <button onClick={(e) => saveQuestion(e)} className="cat-button">
              Submit
            </button>
            <Link to={`/quizzes/${quizId ? quizId : id}/questions`}>
              <button>Cancel</button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddQuestion;
