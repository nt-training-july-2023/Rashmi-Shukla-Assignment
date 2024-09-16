package com.project.assesmentportal.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a category.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    /**
     * The unique identifier of the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    /**
     * The title of the category.
     */
    @Column(name = "cat_title", unique = true, nullable = false)
    private String categoryTitle;

    /**
     * The description of the category.
     */
    @Column(name = "cat_desc")
    private String categoryDescription;

    /**
     * One to many relation between category and quiz.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    /**
     * getter for list of quizzes belonging to this category.
     * @return list of quizzes.
     */
    public final List<Quiz> getQuizzes() {
        return new ArrayList<>(quizzes);
    }

    /**
     * setter for list of quizzes belonging to this category.
     * @param quizzesList list of quizzes.
     */
    public final void setQuizzes(final List<Quiz> quizzesList) {
        this.quizzes = new ArrayList<>(quizzesList);
    }

    /**
     * all args constructor of category.
     * @param id    unique id of category.
     * @param title title of category.
     * @param description  description of category.
     */
    public Category(final long id, final String title,
            final String description) {
        this.categoryId = id;
        this.categoryTitle = title;
        this.categoryDescription = description;
    }
}
