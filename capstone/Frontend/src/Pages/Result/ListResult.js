import React, { useEffect, useState } from "react";
import Navbar from "../../Components/Navbar/Navbar";
import ResultService from "../../Services/ResultService";
import "./Result.css";
import PageHeader from "../../Components/Header/PageHeader";
import Table from "../../Components/Table/Table";
import NoDataAvailable from "../../Components/NoDataAvailable/NoDataAvailable";

const ListResult = () => {
  const [results, setResults] = useState([]);
  const userRole = localStorage.getItem("role");
  const userEmail = localStorage.getItem("userEmail");

  useEffect(() => {
    if (userRole === "admin") {
      getAllResults();
    } else {
      getResultByUserEmail();
    }
  }, [userRole]);

  const getAllResults = () => {
    ResultService.getAllResults()
      .then((response) => {
        setResults(response.data);
      })
      .catch((error) => {});
  };

  const getResultByUserEmail = () => {
    ResultService.getResultByUserEmail(userEmail)
      .then((response) => {
        setResults(response.data);
      })
      .catch((error) => {});
  };

  return (
    <div className="res-page-container">
      <Navbar />
      <div className="res-table-container">
        <PageHeader
          className="result-header"
          heading="RESULTS"
          displayButton=""
          isTest=""
          timer=""
          onClick=""
        />

        {results.length === 0 ? (
          <NoDataAvailable />
        ) : (
          <Table
            className="res-table"
            rows={[
              "SNo.",
              "User Name",
              "User Email",
              "Quiz",
              "Category",
              "Total Questions",
              "Attempted Questions",
              "Obtained Marks",
              "Total Marks",
              "Date & Time",
            ]}
            responseData={results}
            fields={[
              "userName",
              "userEmail",
              "quizTitle",
              "categoryTitle",
              "totalQuestions",
              "attemptedQuestions",
              "obtainedMarks",
              "totalMarks",
              "dateTime",
            ]}
            displayButtons=""
            deleteFunction=""
          />
        )}
      </div>
    </div>
  );
};

export default ListResult;
