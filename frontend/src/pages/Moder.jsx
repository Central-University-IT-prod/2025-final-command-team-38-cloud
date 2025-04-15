  import React, { useState, useEffect } from 'react';
  import axios from 'axios';
  import Header from '../components/Home/Header';


  export default function Moder() {
    const [pendingMentors, setPendingMentors] = useState([]);

    // Функция для получения списка менторов
    const fetchMentors = async () => {
      try {
        const response = await axios.get(import.meta.env.VITE_BACKEND_BASE_URL + '/moderator/requests?limit=5&offset=0', {
          headers: {
            Authorization: "string", // Замените на ваш токен
          }
        });
  
        if (response.status === 200) {
          setPendingMentors(response.data); // Обновляем список менторов
          console.log(response.data); // Логируем полученные данные
        } else {
          console.error(`Ошибка: ${response.status} ${response.statusText}`);
        }
      } catch (error) {
        console.error('Ошибка при получении менторов:', error);
      }
    };
  
    // Функция для принятия ментора
    const handleApprove = async (id, approv) => {
      try {
        const response = await fetch(import.meta.env.VITE_BACKEND_BASE_URL + `/moderator/approve`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json", 
            Authorization: "string", // Замените на ваш токен
          },
          body: JSON.stringify({
            mentorRequestId: id,
            approve: approv,
          })
        });
  
        if (response.status === 200) {
          fetchMentors(); // Обновляем список менторов после принятия/отклонения
        } else {
          console.error('Ошибка при принятии ментора:', response.status);
        }
      } catch (error) {
        console.error('Ошибка при принятии ментора:', error);
      }
    };
  
    // Используем useEffect для загрузки менторов при монтировании компонента
    useEffect(() => {
      fetchMentors(); // Загружаем менторов при монтировании
    }, []); // Пустой массив зависимостей

    return (
      <div className="bg-gradient-to-b from-black to-blue-800 min-h-screen text-white">
      <Header />
      <div className="pt-24 px-4 md:px-8">
        <h1 className="text-3xl md:text-4xl font-bold mb-8 text-center">
          Запросы на модерацию
        </h1>
        {pendingMentors.length === 0 ? (
          <p className="text-center text-gray-300">Запросов на модерацию нет.</p>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full bg-black/70 rounded-lg shadow-lg">
              <thead>
                <tr>
                  <th className="py-3 px-4 text-left font-semibold">Фото</th>
                  <th className="py-3 px-4 text-left font-semibold">Описание</th>
                </tr>
              </thead>
              <tbody>
                {pendingMentors.map(mentor => (
                  <React.Fragment key={mentor.id}>
                    <tr className="border-t border-gray-600">
                      <td className="py-4 px-4 align-top">
                        <img
                          src={mentor.photo}
                          alt={mentor.name}
                          className="w-42 h-38"
                        />
                      </td>
                      <td className="py-4 px-4">
                        <h3 className="text-2xl font-bold text-white">{mentor.name}</h3>
                        <p className="text-Theme font-semibold">{mentor.expertise}</p>
                        <p
                          className="mt-2 text-white overflow-hidden text-left"
                          style={{
                            display: '-webkit-box',
                            WebkitLineClamp: 2,
                            WebkitBoxOrient: 'vertical',
                            maxHeight: '50px'
                          }}
                        >
                          {mentor.bio}
                        </p>
                        <p className="mt-2 text-gray-300 italic">Опыт: {mentor.experience}</p>
                        <div className="mt-2">
                          <div className="flex flex-wrap gap-2 mt-1">
                            {mentor.stack.map(skill => (
                              <span
                                key={skill}
                                className="mt-[5px] text-[14px] leading-[15px] px-[5px] py-[2px] text-white bg-Theme border border-gray-300 rounded-lg whitespace-nowrap cursor-pointer hover:bg-Theme/70 hover:text-[#fff] transition-transform duration-300 hover:scale-105"
                              >
                                {skill}
                              </span>
                            ))}
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr className="border-b border-gray-600">
                      <td colSpan="2" className="py-4 px-4">
                        <div className="flex justify-center space-x-6">
                          <button
                            onClick={() => handleApprove(mentor.id, true)}
                            className="w-full md:w-auto border border-white hover:bg-bgButton/70 text-white py-2                         px-6 rounded transition-colors duration-200 text-lg cursor-pointer"
                          >
                            Принять
                          </button>
                          <button
                            onClick={() => handleApprove(mentor.id, false)}
                            className="w-full md:w-auto border border-white hover:bg-red-700/70 
                            text-white py-2 px-6 rounded transition-colors duration-200 text-lg cursor-pointer"
                          >
                            Отклонить
                          </button>
                        </div>
                      </td>
                    </tr>
                  </React.Fragment>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}