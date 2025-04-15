// src/components/Home.js
import React from 'react';
import { FaUser, FaClipboardCheck, FaTasks } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import Header from '../components/Home/Header';

const Home = () => {
  return (
    <div className="bg-gradient-to-b from-black to-blue-800 text-gray-800 text-center min-h-screen relative pt-10">
      <Header/>
      <h2 className="text-3xl md:text-4xl lg:text-5xl font-[Courier New] font-bold mb-4 text-white z-10 px-4">
        Добро пожаловать в MentorFinder
      </h2>
      <p className="text-lg md:text-2xl mb-8 text-white z-10 px-4">
        Найдите идеального наставника для вашего пути!
      </p>

      <div className="flex justify-center mb-10 z-10 px-4">
        <Link to="/mentors">
          <button className="text-[#fff] text-lg bg-bgButton hover:bg-bgButton/70 px-6 py-3 lg:px-8 lg:py-4 
            rounded transition duration-200 shadow-md transform cursor-pointer">
            Найти ментора
          </button>
        </Link>
      </div>

      <div className=" z-10 mt-20 lg:mt-30 px-4 xl:px-0">
        <h3 className="text-2xl md:text-3xl mb-6 text-white text-center">
          Начни карьеру в IT сегодня!
        </h3>
        <div className="w-[98%] grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-7xl mx-auto">
          <div className="bg-black/70 shadow-lg text-[#fff] p-6 md:p-8 rounded-lg">
            <h4 className="text-lg text-left md:text-2xl font-bold mb-2 flex ">
              <FaUser className="mr-2 text-[#4da6ff] text-3xl md:text-4xl mt-3" />
              Шаг 1: Выберите своего наставника
            </h4>
            <p className="text-base text-left md:text-lg">
              Просмотрите профили наших опытных наставников и выберите того, кто соответствует вашим интересам и целям. Узнайте о их опыте и подходе к обучению.
            </p>
          </div>

          <div className="bg-black/70 shadow-lg text-[#fff] p-6 md:p-8 rounded-lg">
            <h4 className="text-xl text-left md:text-2xl font-bold mb-2 flex items-center">
              <FaClipboardCheck className="mr-2 text-[#4da6ff] text-3xl md:text-4xl" />
              Шаг 2: Оставьте заявку на наставничество
            </h4>
            <p className="text-base text-left md:text-lg">
              Заполните простую форму заявки, чтобы связаться с выбранным ментором. Укажите ваши цели и ожидания, чтобы он мог подготовиться к первой встрече.
            </p>
          </div>

          <div className="bg-black/70 shadow-lg text-[#fff] p-6 md:p-8 rounded-lg">
            <h4 className="text-xl md:text-2xl font-bold mb-2 flex items-center">
              <FaTasks className="mr-2 text-[#4da6ff] text-3xl md:text-4xl" />
              Шаг 3: Начните занятия
            </h4>
            <p className="text-base text-left md:text-lg">
              После согласования времени встречи начните занятия с вашим наставником. Получайте обратную связь и развивайте свои навыки в комфортной и поддерживающей обстановке.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;