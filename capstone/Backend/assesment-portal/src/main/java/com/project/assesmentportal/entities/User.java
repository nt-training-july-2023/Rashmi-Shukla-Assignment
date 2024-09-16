package com.project.assesmentportal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increments
                                                        // the primary key
    private long userId;

    /**
     * The first name of the user.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The password of the user.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The email address of the user.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * The phone number of the user.
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * The role of the user. Default value is "user".
     */
    @Column(nullable = false)
    private String role = "user";
}
