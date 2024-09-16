package com.project.assesmentportal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a question.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    /**
     * The unique identifier of the question.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    /**
     * The title of the question.
     */
    @Column(name = "question_title", nullable = false)
    private String questionTitle;

    /**
     * The first option as answer.
     */
    @Column(nullable = false)
    private String optionOne;

    /**
     * The second option as answer.
     */
    @Column(nullable = false)
    private String optionTwo;

    /**
     * The third option as answer.
     */
    @Column(nullable = false)
    private String optionThree;

    /**
     * The fourth option as answer.
     */
    @Column(nullable = false)
    private String optionFour;

    /**
     * The correct answer.
     */
    @Column(nullable = false)
    private String answer;

    /**
     * many to one relation between quiz and question.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Quiz quiz;

    /**
     * getter for quiz.
     * @return quiz belongs to.
     */
    public final Quiz getQuiz() {
        return new Quiz(quiz.getQuizId(), quiz.getQuizTitle(),
                quiz.getQuizDescription(), quiz.getQuizTimer(),
                quiz.getCategory());
    }

    /**
     * setter for the quiz.
     * @param quizEntity which the quiz belongs to.
     */
    public final void setQuiz(final Quiz quizEntity) {
        this.quiz = new Quiz(quizEntity.getQuizId(),
                quizEntity.getQuizTitle(), quizEntity.getQuizDescription(),
                quizEntity.getQuizTimer(), quizEntity.getCategory());
    }

    /**
     * all args constructor for question.
     * @param id      id for question
     * @param title   title for question
     * @param option1 option 1
     * @param option2 option 2
     * @param option3 option 3
     * @param option4 option 4
     * @param ans     correct answer
     */
    public Question(final long id, final String title,
            final String option1, final String option2,
            final String option3, final String option4, final String ans) {
        this.questionId = id;
        this.questionTitle = title;
        this.optionOne = option1;
        this.optionTwo = option2;
        this.optionThree = option3;
        this.optionFour = option4;
        this.answer = ans;
    }
}
