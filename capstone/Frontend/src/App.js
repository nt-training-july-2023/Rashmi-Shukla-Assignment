import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"
import LoginForm from './Pages/Authentication/LoginForm';
import RegistrationForm from './Pages/Authentication/RegistrationForm';
import Dashboard from './Pages/Dashboard/Dashboard';
import ListCategory from './Pages/Category/ListCategory';
import AddCategory from './Pages/Category/AddCategory';
import UserDashboard from './Pages/Dashboard/UserDashboard';
import ErrorPage from './Pages/ErrorPage/ErrorPage';
import ListQuiz from './Pages/Quiz/ListQuiz';
import AddQuiz from './Pages/Quiz/AddQuiz';
import ListQuestion from './Pages/Question/ListQuestion';
import AddQuestion from './Pages/Question/AddQuestion';
import ListResult from './Pages/Result/ListResult';

function App() {

  return (
     <Router>
        <Routes>
          <Route exact path='/' Component={LoginForm}></Route>
          <Route exact path='/register' Component={ RegistrationForm}></Route>
          <Route exact path='/dashboard' element={<PrivateRoute Component={Dashboard} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/categories' element={<PrivateRoute Component={ListCategory} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/categories/add' element={<PrivateRoute Component={AddCategory} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/categories/update/:id' element={<PrivateRoute Component={AddCategory} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/user-dashboard' element={<PrivateRoute Component={UserDashboard} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/error-page' element={<PrivateRoute Component={ErrorPage} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes' element={<PrivateRoute Component={ListQuiz} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/categories/:id/quizzes' element={<PrivateRoute Component={ListQuiz} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes/add' element={<PrivateRoute Component={AddQuiz} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes/update/:id' element={<PrivateRoute Component={AddQuiz} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes/:id/questions' element={<PrivateRoute Component={ListQuestion} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes/:id/questions/add' element={<PrivateRoute Component={AddQuestion} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/quizzes/:quizId/questions/update/:questionId' element={<PrivateRoute Component={AddQuestion} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
          <Route exact path='/results' element={<PrivateRoute Component={ListResult} isLoggedIn={localStorage.getItem("IsloggedIn")} />}/>
        </Routes>
       </Router>
  );
}

const PrivateRoute = ({ Component }) => {
  const isLoggedIn = localStorage.getItem('IsLoggedIn')
  return isLoggedIn ? <Component /> : <Navigate to="/" replace />;
}

export default App;
