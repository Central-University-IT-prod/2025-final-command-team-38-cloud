import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Modal from '../Modal';
import { FaUserPlus } from 'react-icons/fa';
import { RegistStudent } from '../forms/RegistStudent';

const Header = () => {
  const [visible, setVisible] = useState(false);
  const navigate = useNavigate(); // Исправление названия переменной
  const mentorId = localStorage.getItem('idMentor'); // Получаем ID наставника из localStorage

  const toggleModal = () => {
    setVisible(!visible);
  };

  // Функция для навигации
  const handleNavigate = () => {
    navigate('/regist'); // Используем функцию навигации при вызове
  };

  // Функция для перехода в профиль
  const handleProfileNavigate = () => {
    navigate(`/profile`); // Переход к профилю наставника
  };

  return (
    <>
      <header className="fixed top-0 left-0 right-0 p-4 flex justify-between items-center text-white bg-black bg-opacity-30 backdrop-blur-md shadow-lg z-50">
        <div className="flex items-center">
          <h1 className="text-2xl font-bold transition-transform duration-300 hover:scale-105 max-[415px]:text-lg">
            <Link to="/">{"<MentorFinder/>"}</Link>
          </h1>
        </div>
        <nav className="flex space-x-6">
          <ul className="flex space-x-6">
            <li>
              {mentorId ? (
                // Если mentorId есть, показываем кнопку для перехода в профиль
                <button
                  onClick={handleProfileNavigate}
                  className="bg-black border-1 rounded-lg border-white px-4 py-2 transition duration-200 shadow-md transform cursor-pointer flex items-center max-[360px]:text-xs"
                >
                  Профиль
                </button>
              ) : (
                // Если mentorId нет, показываем кнопку для регистрации наставника
                <button
                  onClick={handleNavigate}
                  className="bg-black border-1 rounded-lg border-white px-4 py-2 transition duration-200 shadow-md transform cursor-pointer flex items-center max-[360px]:text-xs"
                >
                  <FaUserPlus className="mr-2" />
                  Стать наставником
                </button>
              )}
            </li>
          </ul>
        </nav>
      </header>
      <div className="mt-16">
        <Modal visible={visible} setVisible={setVisible}>
          <RegistStudent setVisible={setVisible} />
        </Modal>
      </div>
    </>
  );
};

export default Header;
