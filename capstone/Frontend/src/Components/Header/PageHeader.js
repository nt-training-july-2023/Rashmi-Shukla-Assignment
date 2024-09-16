import React from 'react'

const PageHeader =  ({className, heading, displayButton, onClick, name, isTest, timer,submitted }) => {
  const userRole = localStorage.getItem("role");
  const totalMarks = parseInt(localStorage.getItem("totalMarks"), 10);

  const renderButton = () => (
    <button onClick={onClick}>
      {name}
    </button>
  );

  const renderQuizControls = () => (
    <>
      <p>Time Remaining: {timer}</p>
      <button onClick={onClick} disabled={isNaN(totalMarks)}>
        Submit Quiz
      </button>
    </>
  );

  return (
    <div className={className}>
      <h1>{heading}</h1>
      {displayButton && userRole === "admin" ? renderButton() : isTest && renderQuizControls()}
    </div>
  )

}

export default PageHeader
