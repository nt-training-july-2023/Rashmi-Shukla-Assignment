package com.project.assesmentportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.assesmentportal.entities.Result;

/**
 * JPA repository interface for managing Result entities.
 */
public interface ResultRepository extends JpaRepository<Result, Long> {
    /**
     * retrieves results of user by their email.
     * @param userEmail email of the user.
     * @return list of results of that user.
     */
    List<Result> findByUserEmail(String userEmail);
}
