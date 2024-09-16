import axios from 'axios';

const QUESTION_BASE_URL = 'http://localhost:8080/question';

class QuestionService{

    addQuestion(Question){
        return axios.post(QUESTION_BASE_URL, Question);
    }

    getAllQuestions(){
        return axios.get(QUESTION_BASE_URL);
    }

    getQuestionById(id){
        return axios.get(QUESTION_BASE_URL+'/'+id);
    }

    updateQuestion(id, Question){
        return axios.put(QUESTION_BASE_URL+'/'+id,Question);
    }

    deleteQuestion(id){
        return axios.delete(QUESTION_BASE_URL+'/'+id);
    } 
}

export default new QuestionService();