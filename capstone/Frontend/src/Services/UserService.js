import axios from 'axios';

const USER_BASE_URL = "http://localhost:8080/users";

class UserService {

    register(user){
        return axios.post(USER_BASE_URL+"/register", user);
    }

    getAllUsers(){
        return axios.get(USER_BASE_URL);
    }

    login(user){
        return axios.post(USER_BASE_URL+"/login",user);
    }

}

export default new UserService();