import React, { useEffect, useState } from "react";
import "./Quiz.css";
import Swal from "sweetalert2";
import { Link, useNavigate, useParams } from "react-router-dom";
import Navbar from "../../Components/Navbar/Navbar";
import ErrorPage from "../ErrorPage/ErrorPage";
import QuizService from "../../Services/QuizService";
import CategoryService from "../../Services/CategoryService";

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [quizDescription, setQuizDescription] = useState("");
  const [quizTimer, setQuizTimer] = useState();
  const [selectedCategory, setSelectedCategory] = useState();
  const [category, setCategory] = useState(null);
  const [categories, setCategories] = useState([]);
  const [errors, setErrors] = useState("");
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    getAllCategories();
  },[]);

  const getAllCategories = () => {
    CategoryService.getAllCategories()
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
      });
  };

  const validateForm = () => {
    if (quizTitle === "" || quizDescription === "" ||isNaN(quizTimer)||category===null) {
      setErrors("*all the fields are mandatory");
      return true;
    }
    if (quizTimer % 1 !== 0 || quizTimer <= 0) {
      setErrors("*timer should be a positive integer");
      return true;
    }
    return false;
  };

  const handleCategoryChange = (event) => {
    const categoryId = event.target.value;
    CategoryService.getCategoryById(categoryId)
      .then((response) => {
        const catObject = response.data;
        setCategory(catObject);
      })
      .catch((error) => {
      });
    setSelectedCategory(categoryId);
  };

  const saveQuiz = (e) => {
    e.preventDefault();

    const parsedQuizTimer = parseInt(quizTimer);

    if (!validateForm()) {
      const quiz = { quizTitle, quizDescription, category, quizTimer };
      if (id) {
        QuizService.updateQuiz(id, quiz)
          .then((response) => {
            Swal.fire({
              title: "Success",
              text: response.data.message,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            });
            navigate("/quizzes");
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
        QuizService.addQuiz(quiz)
          .then((response) => {
            Swal.fire({
              title: "Success",
              text: response.data.message,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            });
            navigate("/quizzes");
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

  useEffect(() => {
    if (id) {
      QuizService.getQuizById(id)
        .then((response) => {
          setQuizTitle(response.data.quizTitle);
          setQuizDescription(response.data.quizDescription);
          setQuizTimer(response.data.quizTimer);
          setCategory(response.data.category);
          setSelectedCategory(response.data.category.categoryId);
        })
        .catch((error) => {
        });
    }
  }, [id]);

  const heading = () => {
    if (id) {
      return <h2 style={{ textAlign: "center" }}>UPDATE QUIZ</h2>;
    } else {
      return <h2 style={{ textAlign: "center" }}>ADD QUIZ</h2>;
    }
  };

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
          <label>Quiz Title</label>

          <input
            type="text"
            value={quizTitle}
            onChange={(e) => {
              setQuizTitle(e.target.value);
              setErrors("");
            }}
          />

          <label>Quiz Description </label>
          <input
            type="text"
            value={quizDescription}
            onChange={(e) => {
              setQuizDescription(e.target.value);
              setErrors("");
            }}
          />

          <label>Quiz Timer </label>
          <input
            type="number"
            value={quizTimer}
            onChange={(e) => {
              setQuizTimer(e.target.value);
              setErrors("");
            }}
          />

          <label htmlFor="category-select">Category:</label>
          <select
            id="category-select"
            value={selectedCategory}
            onChange={handleCategoryChange}
          >
            <option value="">--Select a category--</option>
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryTitle}
              </option>
            ))}
          </select>

          <span>{errors}</span>

          <div>
            <button onClick={(e) => saveQuiz(e)} className="cat-button">
              Submit
            </button>
            <Link to="/quizzes">
              <button>Cancel</button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddQuiz;
