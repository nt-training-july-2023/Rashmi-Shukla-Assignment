import axios from 'axios';

const QUIZ_BASE_URL = 'http://localhost:8080/quiz';

class QuizService{

    addQuiz(Quiz){
        return axios.post(QUIZ_BASE_URL, Quiz);
    }

    getQuizzes(){
        return axios.get(QUIZ_BASE_URL);
    }

    getQuizById(id){
        return axios.get(QUIZ_BASE_URL+'/'+id);
    }

    updateQuiz(id, Quiz){
        return axios.put(QUIZ_BASE_URL+'/'+id,Quiz);
    }

    deleteQuiz(id){
        return axios.delete(QUIZ_BASE_URL+'/'+id);
    } 

    getQuestionsByQuiz(id){
        return axios.get(QUIZ_BASE_URL+'/'+id+'/'+"questions");
    }
}

export default new QuizService();