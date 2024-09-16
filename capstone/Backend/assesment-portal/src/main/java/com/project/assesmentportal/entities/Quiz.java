package com.project.assesmentportal.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a quiz.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quizes")
public class Quiz {
    /**
     * The unique identifier of the quiz.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizId;

    /**
     * The title of the quiz.
     */
    @Column(name = "quiz_title", nullable = false, unique = true)
    private String quizTitle;

    /**
     * The description of the quiz.
     */
    @Column(name = "quiz_desc")
    private String quizDescription;

    /**
     * The timer for quiz.
     */
    @Column(nullable = false)
    private int quizTimer;

    /**
     * many to one relation between category and quiz.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Category category;

    /**
     * getter for category.
     * @return category the quiz belongs to.
     */
    public final Category getCategory() {
        return new Category(category.getCategoryId(),
                category.getCategoryTitle(),
                category.getCategoryDescription());
    }

    /**
     * setter for the category.
     * @param categoryEntity which the quiz belongs to.
     */
    public final void setCategory(final Category categoryEntity) {
        this.category = new Category(categoryEntity.getCategoryId(),
                categoryEntity.getCategoryTitle(),
                categoryEntity.getCategoryDescription());
    }

    /**
     * One to many relation between quiz and question.
     */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    /**
     * getter for list of questions belonging to this quiz.
     * @return list of questions.
     */
    public final List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * setter for list of questions belonging to this quiz.
     * @param questionsList list of questions.
     */
    public final void setQuestions(final List<Question> questionsList) {
        this.questions = new ArrayList<>(questionsList);
    }

    /**
     * all args constructor for quiz.
     * @param id             unique id for quiz.
     * @param title          title of the quiz.
     * @param description    description of the quiz.
     * @param time           timer for the quiz.
     * @param categoryEntity category.
     */
    public Quiz(final long id, final String title,
            final String description, final int time,
            final Category categoryEntity) {
        this.quizId = id;
        this.quizTitle = title;
        this.quizDescription = description;
        this.quizTimer = time;
        this.category = new Category(categoryEntity.getCategoryId(),
                categoryEntity.getCategoryTitle(),
                categoryEntity.getCategoryDescription());
    }
}
