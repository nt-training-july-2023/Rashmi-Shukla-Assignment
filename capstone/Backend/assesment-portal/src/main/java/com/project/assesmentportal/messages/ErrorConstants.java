package com.project.assesmentportal.messages;

/**
 * This class defines error messages for application.
 */
public final class ErrorConstants {
    /**
     * constructor for class.
     */
    private ErrorConstants() {

    }

//VALIDATIONS
    /**
     * Error message for CategoryDto: "Category title is required".
     */
    public static final String CATEGORY_TITLE_REQUIRED =
            "Category title is required";

    /**
     * Error message for CategoryDto: "Category description is required".
     */
    public static final String CATEGORY_DESCRIPTION_REQUIRED =
            "Category description is required";

    /**
     * Error message for LoginRequestDto: "Email is required".
     */
    public static final String EMAIL_REQUIRED = "Email is required";

    /**
     * Error message for LoginRequestDto: "Password is required".
     */
    public static final String PASSWORD_REQUIRED = "Password is required";

    /**
     * Error message for QuestionDto: "Question title is required".
     */
    public static final String QUESTION_TITLE_REQUIRED =
            "Question title is required";

    /**
     * Error message for QuestionDto: "Options are required".
     */
    public static final String OPTIONS_REQUIRED = "Options are required";

    /**
     * Error message for QuestionDto: "Answer is required".
     */
    public static final String ANSWER_REQUIRED = "Answer is required";

    /**
     * Error message for QuestionDto: "Quiz is required".
     */
    public static final String QUIZ_REQUIRED = "Quiz is required";

    /**
     * Error message for QuizDto: "Quiz title is required".
     */
    public static final String QUIZ_TITLE_REQUIRED =
            "Quiz title is required";

    /**
     * Error message for QuizDto: "Quiz Description is required".
     */
    public static final String QUIZ_DESCRIPTION_REQUIRED =
            "Quiz Description is required";

    /**
     * Error message for QuizDto: "Quiz timer cannot be zero".
     */
    public static final String QUIZ_TIMER_MIN =
            "Quiz timer cannot be zero";

    /**
     * Error message for QuizDto: "Category required".
     */
    public static final String CATEGORY_REQUIRED =
            "Category for quiz is reuqired";

    /**
     * Error message for ResultDto: "Enter valid total marks".
     */
    public static final String TOTAL_MARKS_MIN = "Enter valid total marks";

    /**
     * Error message for ResultDto: "Enter valid obtained marks".
     */
    public static final String OBTAINED_MARKS_MIN =
            "Enter valid obtained marks";

    /**
     * Error message for ResultDto: "Enter valid Attempted Questions.".
     */
    public static final String ATTEMPTED_QUESTIONS_MIN =
            "Enter valid Attempted Questions.";

    /**
     * Error message for ResultDto: "Date and time is required".
     */
    public static final String DATE_TIME_REQUIRED =
            "Date and time is required";

    /**
     * Error message for ResultDto: "Email domain must be @nucleusteq.com".
     */
    public static final String USER_EMAIL_DOMAIN =
            "Email domain must be @nucleusteq.com";

    /**
     * Error message for ResultDto: "User name is required".
     */
    public static final String USER_NAME_REQUIRED =
            "User name is required";

    /**
     * Error message for UserDto: "First name is required".
     */
    public static final String FIRST_NAME_REQUIRED =
            "First name is required";

    /**
     * Error message for UserDto: "Enter a valid First Name".
     */
    public static final String FIRST_NAME_PATTERN =
            "Enter a valid First Name";

    /**
     * Error message for UserDto: "Last name is required".
     */
    public static final String LAST_NAME_REQUIRED =
            "Last name is required";

    /**
     * Error message for UserDto: "Enter a valid Last Name".
     */
    public static final String LAST_NAME_PATTERN =
            "Enter a valid Last Name";

    /**
     * Error message for UserDto: "Phone number is required".
     */
    public static final String PHONE_NUMBER_REQUIRED =
            "Phone number is required";

    /**
     * Error message for UserDto: "Phone number must be a 10-digit
     * number.".
     */
    public static final String PHONE_NUMBER_PATTERN =
            "Phone number must be a 10-digit number";

    /**
     * Error message for Options: "OptionI is required".
     */
    public static final String OPTIONI_REQUIRED = "Option I is required";

    /**
     * Error message for Options: "OptionII is required".
     */
    public static final String OPTIONII_REQUIRED = "Option II is required";

    /**
     * Error message for Options: "OptionIII is required".
     */
    public static final String OPTIONIII_REQUIRED =
            "Option III is required";

    /**
     * Error message for Options: "OptionIV is required".
     */
    public static final String OPTIONIV_REQUIRED = "Option IV is required";

//EXCEPTIONS
    /**
     * Exception message for Category Service: "Category already exists".
     */
    public static final String CATEGORY_ALREADY_EXISTS =
            "Category with same title already exists";

    /**
     * Exception message for Category Service: "Category already exists".
     */
    public static final String CATEGORY_DOESNOT_EXISTS =
            "Category not found, ID:";

    /**
     * Exception message for Question Service: "Question doesn't exists".
     */
    public static final String QUESTION_DOESNOT_EXISTS =
            "Question not found, ID:";

    /**
     * Exception message for Question Service: "Answer not within options".
     */
    public static final String ANSWER_NOT_IN_OPTIONS =
            "Correct Answer not within options";

    /**
     * Exception message for Quiz Service: "Quiz already exists".
     */
    public static final String QUIZ_ALREADY_EXISTS =
            "Quiz with same title already exists";

    /**
     * Exception message for Quiz Service: "Quiz doesn't exists".
     */
    public static final String QUIZ_DOESNOT_EXISTS = "Quiz not found, ID:";

    /**
     * Exception message for User Service: "Email-id already registered".
     */
    public static final String EMAIL_ALREADY_REGISTERED =
            "Email-id already registered";

    /**
     * Exception message for User Service: "Invalid Credentials".
     */
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
}
