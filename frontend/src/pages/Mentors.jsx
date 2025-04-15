import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Header from '../components/Home/Header';

const Mentors = () => {
  const [mentors, setMentors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMentors = async () => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/mentor`);
        setMentors(response.data);
        setLoading(false);
      } catch (err) {
        setError('Error fetching mentors');
        setLoading(false);
      }
    };

    fetchMentors();
  }, []);

  if (loading) {
    return <div className="text-center">Загрузка...</div>;
  }

  if (error) {
    return <div className="text-white text-center">{error}</div>;
  }

  return (
    <div className="bg-gradient-to-b from-black to-blue-800 min-h-screen pt-4">
      <Header />
      <h2 className="text-5xl font-bold text-center mb-8 text-white max-[420px]:text-3xl">
        Все менторы
      </h2>
      <div className="grid grid-cols-1 gap-8">
        {mentors.map((mentor) => (
          <div
            key={mentor.id}
            className="bg-black/70 w-[90%] p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300 mx-auto"
          >
            <div className="flex flex-col md:flex-row items-center md:items-start justify-between">
              <div className="flex-shrink-0">
                <Link to={`/mentors/${mentor.id}`}>
                  <img
                    src={mentor.photo}
                    alt={`${mentor.firstName} ${mentor.lastName}`}
                    className="w-34 h-34 object-cover rounded-full"
                  />
                </Link>
              </div>
              <div className="flex-1 text-center md:text-left md:ml-6">
                <Link to={`/mentors/${mentor.id}`}>
                  <h3 className="text-2xl font-bold text-white">
                    {mentor.firstName} {mentor.lastName}
                  </h3>
                </Link>
                <div className="mt-2 flex flex-wrap gap-2 justify-center md:justify-start">
                  {mentor.stack && mentor.stack.map((skill) => (
                    <span
                      key={skill}
                      className="text-[14px] px-[5px] py-[2px] text-white bg-Theme border border-gray-300 rounded-lg whitespace-nowrap cursor-pointer hover:bg-Theme/70 hover:text-[#fff] transition-transform duration-300"
                    >
                      {skill}
                    </span>
                  ))}
                </div>
                <p className="text-Theme font-semibold mt-2">{mentor.expertise}</p>
                <p
                  className="mt-2 text-white overflow-hidden text-left"
                  style={{
                    display: '-webkit-box',
                    WebkitLineClamp: 2,
                    WebkitBoxOrient: 'vertical',
                    maxHeight: '50px',
                  }}
                >
                  {mentor.bio}
                </p>
                <p className="mt-2 text-gray-300 italic">Опыт: {mentor.experience}</p>
                <div className="mt-4 flex flex-wrap gap-4 justify-center md:justify-start">
                  <div
                    href={mentor.linkedin}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center text-Theme transition-colors duration-200"
                  >
                    <svg className="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 24 24">
                      <path d="M19 0h-14c-2.761 0-5 2.239-5 5v14c0 2.761 2.239 5 5 5h14c2.762 0 5-2.239 5-5v-14c0-2.761-2.238-5-5-5zm-11 19h-3v-10h3v10zm-1.5-11.25c-.966 0-1.75-.784-1.75-1.75s.784-1.75 1.75-1.75 1.75.784 1.75 1.75-.784 1.75-1.75 1.75zm13.5 11.25h-3v-5.5c0-1.379-.028-3.156-1.922-3.156-1.923 0-2.219 1.5-2.219 3.053v5.603h-3v-10h2.882v1.367h.041c.402-.761 1.386-1.562 2.853-1.562 3.051 0 3.615 2.007 3.615 4.619v5.576z" />
                    </svg>
                    LinkedIn
                  </div>
                  <div
                    href={mentor.github}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="flex items-center text-Theme hover:text-[#353535] transition-colors duration-200"
                  >
                    <svg className="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 24 24">
                      <path d="M12 0c-6.627 0-12 5.373-12 12 0 5.302 3.438 9.8 8.205 REDACTED.82-.261.82-.577 0-.285-.011-1.04-.017-2.04-3.338.726-4.042-1.609-4.042-1.609-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.085 1.84 1.237 1.84 1.237 1.07 1.834 2.809 1.304 REDACTED-.775.418-1.304.762-1.604-2.665-.303-5.466-1.332-5.466-5.93 0-1.312.469-2.382 1.236-3.221-.124-.303-.536-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.958-.266 1.984-.399 3.003-.404 1.018.005 2.045.138 3.003.404 2.291-1.552 3.297-1.23 3.297-1.23.655 1.653.243 2.874.119 REDACTED 1.235 1.909 1.235 3.221 0 4.61-2.804 5.624-5.475 REDACTED.823 1.102.823 2.222 0 1.606-.015 2.896-.015 3.286 0 .REDACTED.576 4.765-1.59 8.2-6.087 8.2-11.386 0-6.627-5.373-12-12-12z" />
                    </svg>
                    GitHub
                  </div>
                </div>
              </div>
              <div className="text-right mt-3 max-[420px]:text-center md:ml-6">
                <p className="text-lg font-bold text-white">{mentor.rate}</p>
                <div className="mt-4 space-y-2 flex flex-col">
                  <Link to={`/mentors/${mentor.id}`}>
                    <button className="w-full bg-bgButton hover:bg-bgButton/70 text-white py-1 px-4 rounded transition-colors duration-200 cursor-pointer">
                      Подробнее
                    </button>
                  </Link>
                  <button className="w-full bg-bgButton hover:bg-bgButton/70 text-white py-1 px-4 rounded transition-colors cursor-pointer">
                    Оставить заявку
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Mentors;
