package com.project.assesmentportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.assesmentportal.entities.Question;

/**
 * JPA repository interface for managing Question entities.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    /**
     * retrieves question by its title.
     * @param questionTitle title of the question to be found.
     * @return existing question with matching title.
     */
    Optional<Question> findByQuestionTitle(String questionTitle);
}
