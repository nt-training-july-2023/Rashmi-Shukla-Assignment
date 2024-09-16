import axios from 'axios';

const RESULT_BASE_URL = "http://localhost:8080/result";

class ResultService {

    addResult(Result){
        return axios.post(RESULT_BASE_URL, Result);
    }

    getAllResults(){
        return axios.get(RESULT_BASE_URL);
    }

    getResultByUserEmail(userEmail){
        return axios.get(RESULT_BASE_URL+'/'+userEmail);
    }

}

export default new ResultService();