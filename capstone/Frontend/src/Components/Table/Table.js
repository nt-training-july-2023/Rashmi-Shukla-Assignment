import React from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import "./Table.css";
import Button from "../Button/Button";

const Table = ({
  className,
  rows,
  responseData,
  fields,
  displayButtons,
  deleteFunction,
}) => {
  const userRole = localStorage.getItem("role");
  const navigate = useNavigate();
  return (
    <table className={className}>
      <thead>
        <tr>
          {rows.map((row, index) => (
            <th key={index}>{row}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {responseData.map((data, index) => (
          <tr key={index}>
            <td>{++index}</td>
            {fields.map((field, fieldIndex) => (
              <td key={fieldIndex}>{data[field]}</td>
            ))}

            {displayButtons && (
              <td>
                <Button
                  className="view-button"
                  onClick={() =>
                    navigate(`/categories/${data.categoryId}/quizzes`)
                  }
                  name="View Quiz"
                />

                {userRole === "admin" && (
                  <>
                    <Button
                      className="update-button"
                      onClick={() =>
                        navigate(`/categories/update/${data.categoryId}`)
                      }
                      name="Update"
                    />

                    <Button
                      className="delete-button"
                      onClick={() =>
                        Swal.fire({
                          title: "Warning",
                          text: "Delete Category?",
                          icon: "warning",
                          confirmButtonText: "Delete",
                          confirmButtonColor: "red",
                          showCancelButton: true,
                        }).then((result) => {
                          if (result.isConfirmed) {
                            deleteFunction(data.categoryId);
                          }
                        })
                      }
                      name="Delete"
                    />
                  </>
                )}
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;
