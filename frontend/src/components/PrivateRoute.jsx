import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ element: Component, ...rest }) => {
  const isAuthenticated = localStorage.getItem('userData') !== null;

  return isAuthenticated ? <Component {...rest} /> : <Navigate to="/regist" />;
};

export default PrivateRoute;