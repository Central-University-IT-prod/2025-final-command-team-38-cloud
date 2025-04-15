import React, { useState } from 'react';
import Header from '../components/Home/Header';

const Admin = () => {
  
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Здесь вы можете добавить логику для проверки учетных данных
    if (username === 'admin' && password === 'password') {
      // Успешный вход
      alert('Вход успешен!');
      // Здесь можно перенаправить администратора на другую страницу
    } else {
      // Ошибка входа
      setError('Неверное имя пользователя или пароль.');
    }
  };

  return (
    
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-black to-blue-800">
        <Header />
      <div className="bg-black/70 p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-3xl font-bold text-center text-white mb-4">Панель администратора</h2>
        {error && <p className="text-red-500 text-center mb-4">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-white mb-2" htmlFor="password">Пароль</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-2 rounded bg-gray-800 text-white border border-gray-600"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-bgButton hover:bg-bgButton/70 text-white py-2 rounded transition duration-200"
          >
            Войти
          </button>
        </form>
      </div>
    </div>
  );
};

export default Admin;
