import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Header from './components/Home/Header';
import Home from './pages/Home';
import Mentors from './pages/Mentors';
import MentorDetails from './pages/MentorDetails';
import Admin from './pages/Admin';
import Moder from './pages/Moder';
import ModeratorLogin from './components/ModeratorLogin';
import RegistMentor from './components/RegistMentor';
import MentorProfile from './components/MentorProfile';
import AdminConsole from './pages/AdminConsole';

const PrivateRoute = ({ element: Element, ...rest }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      // Можно добавить дополнительную логику для проверки валидности токена
      setIsAuthenticated(true);
    } else {
      setIsAuthenticated(false);
    }
  }, []);

  return isAuthenticated ? <Element {...rest} /> : <Navigate to="/mentors" />;
};

const App = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/mentors" element={<Mentors />} />
        <Route path="/mentors/:id" element={<MentorDetails />} />
        <Route path="/moder" element={<Moder />} />
        <Route path="/moder/login" element={<ModeratorLogin />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/regist" element={<RegistMentor />} />
        <Route path="/profile" element={<MentorProfile />} />
        <Route path="/adminConsole" element={<AdminConsole />} />
        <Route path="/profile" element={<PrivateRoute element={MentorProfile} />} />
        <Route path="/adminConsole" element={<PrivateRoute element={AdminConsole} />} />
      </Routes>
    </Router>
  );
};

export default App;
