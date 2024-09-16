import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import "./Category.css";
import CategoryService from "../../Services/CategoryService";
import Navbar from "../../Components/Navbar/Navbar";
import PageHeader from "../../Components/Header/PageHeader";
import Table from "../../Components/Table/Table";
import NoDataAvailable from "../../Components/NoDataAvailable/NoDataAvailable";

const ListCategory = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getAllCategories();
  }, []);

  const getAllCategories = () => {
    CategoryService.getAllCategories()
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const deleteCategory = (categoryId) => {
    CategoryService.deleteCategory(categoryId)
      .then((response) => {
        Swal.fire({
          title: "Success",
          text: response.data.message,
          icon: "success",
          timer: 2000,
          showConfirmButton: false,
        });
        getAllCategories();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="page-container">
      <Navbar />
      <PageHeader
        className="category-header"
        heading="CATEGORIES"
        displayButton="true"
        onClick={() => navigate(`/categories/add`)}
        name="Add Category"
      />

      {categories.length === 0 ? (
        <NoDataAvailable />
      ) : (
        <div className="table-container">
          <Table
            className="category-table"
            rows={[
              "Category Id",
              "Category title",
              "Category Description",
              "Actions",
            ]}
            responseData={categories}
            fields={["categoryTitle", "categoryDescription"]}
            displayButtons="true"
            deleteFunction={deleteCategory}
          />
        </div>
      )}
    </div>
  );
};

export default ListCategory;
