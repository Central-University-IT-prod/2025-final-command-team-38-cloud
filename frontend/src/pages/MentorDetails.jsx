import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const MentorDetails = () => {
  const { id } = useParams();
  const [mentor, setMentor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMentor = async () => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/mentor/${id}`);
        setMentor(response.data);
        setLoading(false);
      } catch (err) {
        setError('Ошибка при загрузке данных ментора');
        setLoading(false);
      }
    };

    fetchMentor();
  }, [id]);

  if (loading) {
    return <div className="text-center text-white">Загрузка...</div>;
  }

  if (error) {
    return <div className="text-center text-white">{error}</div>;
  }

  if (!mentor) {
    return (
      <div className="min-h-screen flex flex-col justify-center items-center">
        <h2 className="text-3xl font-bold text-white">Ментор не найден</h2>
        <Link to="/mentors" className="mt-4 text-blue-500">
          Вернуться к списку менторов
        </Link>
      </div>
    );
  }

  const fullName = `${mentor.firstName} ${mentor.lastName}`;

  return (
    <div className="bg-gradient-to-r from-black to-blue-800 min-h-screen pt-4 text-white">
      <div className="w-[90%] mx-auto">
        <Link to="/mentors" className="text-white hover:underline transition-colors font-bold">
          &larr; К списку менторов
        </Link>
        <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="md:col-span-1 flex flex-col gap-4">
            <div className="bg-black/70 p-4 rounded-md shadow-md flex flex-col items-center">
              <img
                src={mentor.photo}
                alt={fullName}
                className="w-100 h-140 object-cover rounded"
              />
              <button className="mt-4 bg-bgButton hover:bg-bgButton/70 text-white py-2 px-4 rounded transition-colors duration-200 cursor-pointer">
                Оставить заявку
              </button>
            </div>
            <div className="bg-black/70 p-4 rounded-md shadow-md">
              <h3 className="text-xl font-bold mb-3">Стоимость обучения</h3>
              <p className="mb-2"><strong>Тариф:</strong> {mentor.costPerHour} ₽/час</p>
            </div>
          </div>
          <div className="md:col-span-2 flex flex-col gap-4">
            <div className="bg-black/70 p-6 rounded-md shadow-md">
              <h3 className="text-2xl font-bold mb-4">Информация о менторе</h3>
              <p className="mb-1"><strong>Имя:</strong> {fullName}</p>
              <p className="mb-1"><strong>Возраст:</strong> {mentor.age}</p>
              <p className="mb-1"><strong>Опыт:</strong> {mentor.experience} лет</p>
              <p className="mb-1"><strong>О себе:</strong> {mentor.bio}</p>
            </div>
            <div className="bg-black/70 p-6 rounded-md shadow-md">
              <h3 className="text-2xl font-bold mb-4">Стек технологий</h3>
              {mentor.stack && mentor.stack.length > 0 ? (
                <ul className="flex flex-wrap gap-2">
                  {mentor.stack.map((tech, index) => (
                    <li key={index} className="bg-gray-700 px-3 py-1 rounded">
                      {tech}
                    </li>
                  ))}
                </ul>
              ) : (
                <p>Стек не указан</p>
              )}
            </div>
            <div className="bg-black/70 p-6 rounded-md shadow-md">
              <h3 className="text-2xl font-bold mb-4">Контакты</h3>
              <p className="mb-1"><strong>Email:</strong> {mentor.email}</p>
              <p className="mb-1"><strong>Telegram:</strong> {mentor.telegram || 'Не указан'}</p>
              {mentor.resources && mentor.resources.length > 0 && (
                <div>
                  <p className="mb-1"><strong>Ресурсы:</strong></p>
                  <ul>

                  {mentor.resources.map((resource, index) => (
                      <li key={index}>
                        <a
                          href={resource.url}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="text-blue-300 hover:underline"
                        >
                          {resource.type}
                        </a>
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MentorDetails;
