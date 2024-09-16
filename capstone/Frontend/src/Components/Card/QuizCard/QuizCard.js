import React from 'react'
import Button from '../../Button/Button';
import './QuizCard.css'
import Swal from 'sweetalert2';
import { useNavigate } from 'react-router-dom';
import instructions from '../../../Pages/Quiz/Instructions';

const QuizCard = ({quizItem, deleteQuiz}) => {
  const userRole = localStorage.getItem("role")
  const navigate = useNavigate();
  return (
          <div className="quiz-card">
            <div className="quiz-card-body">
              <h5 className="quiz-card-title">Quiz: {quizItem.quizTitle}</h5>
              <p className="quiz-card-text">{quizItem.quizDescription}</p>
              <p className="quiz-card-text">
                Time limit: {quizItem.quizTimer} minutes
              </p>
              <div className="quiz-card-text">
                <div>
                  <h5>Category: {quizItem.category.categoryTitle}</h5>
                  <h6>{quizItem.category.categoryDescription}</h6>
                </div>
              </div>
              {userRole === "admin" ? (
                <>
                  <Button 
                  className="view-btn"
                  onClick={() =>
                    navigate(`/quizzes/${quizItem.quizId}/questions`)
                  }
                  name= "View Questions" />

                  <Button
                    className="update-btn"
                    onClick={() =>
                      navigate(`/quizzes/update/${quizItem.quizId}`)
                    }
                   name= "Update Quiz" />

                  <Button
                    className="delete-btn"
                    onClick={() =>
                      Swal.fire({
                        title: "Warning",
                        text: "Delete Quiz?",
                        icon: "warning",
                        confirmButtonText: "Delete",
                        confirmButtonColor: "red",
                        showCancelButton: true,
                      }).then((result) => {
                        if (result.isConfirmed) {
                          deleteQuiz(quizItem.quizId);
                        }
                      })
                    }
                    name="Delete Quiz"
                  />
                </>
              ) : (
                <Button
                  className="start-btn"
                  onClick={() =>
                    Swal.fire({
                      title: "Instructions",
                      icon: "info",
                      html: instructions,
                      confirmButtonText: "Start Quiz",
                      showCancelButton: true,
                    }).then((result) => {
                      if (result.isConfirmed) {
                        navigate(`/quizzes/${quizItem.quizId}/questions`);
                      }
                    })
                  }
                  name="Start Assesment"
                />
    
              )}
            </div>
          </div>
  )
}

export default QuizCard
