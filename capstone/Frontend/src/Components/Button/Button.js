import React from "react";
import './Button.css'

const Button = ({ className, onClick, name }) => {
  return (
    <button
      className={`action-buttons ${className}`}
      onClick={onClick}
    >
      {name}
    </button>
  );
};

export default Button;
