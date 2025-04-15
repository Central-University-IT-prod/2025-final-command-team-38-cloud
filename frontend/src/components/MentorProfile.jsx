import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const MentorProfile = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [profile, setProfile] = useState({});
  const [moderationStatus, setModerationStatus] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [photoFile, setPhotoFile] = useState(null);
  const mentorId = localStorage.getItem('idMentor');
  const navigate = useNavigate();

  // Fetch profile and moderation status
  useEffect(() => {
    const fetchMentorStatusAndProfile = async () => {
      try {
        if (!mentorId) {
          // Если нет mentorId в localStorage, можно перенаправить на регистрацию
          // navigate('/register');
          return;
        }
        // Получаем статус модерации
        const responseStatus = await axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/mentor/request/${mentorId}`);
        const status = responseStatus.data.status;

        if (status === 'CANCELED') {
          localStorage.removeItem('idMentor');
        } else if (status === 'WAIT') {
          setModerationStatus('WAIT');
          // alert("Your profile is under moderation, please wait.");
        } else if (status === 'ACTIVE') {
          setModerationStatus('ACTIVE');
          // Получаем данные профиля, если статус активен
          const responseProfile = await axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/mentor/request/${mentorId}`);
          setProfile(responseProfile.data);
        }
      } catch (error) {
        console.error('Ошибка получения данных профиля или статуса модерации:', error);
      }
    };

    fetchMentorStatusAndProfile();
  }, [mentorId, navigate]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProfile((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setPhotoFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setProfile((prev) => ({
          ...prev,
          photo: reader.result,
        }));
      };
      reader.readAsDataURL(file);
    }
  };

  const toggleEdit = () => setIsEditing((prev) => !prev);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    const formData = new FormData();
    formData.append('name', profile.name);
    formData.append('jobTitle', profile.jobTitle);
    formData.append('bio', profile.bio);
    formData.append('linkedin', profile.linkedin);
    formData.append('github', profile.github);
    formData.append('skills', profile.skills.join(', '));
    formData.append('education', profile.education.join(', '));

    if (photoFile) {
      formData.append('photo', photoFile);
    }

    try {
      await axios.put(`${import.meta.env.VITE_BACKEND_BASE_URL}/mentor/${mentorId}`, formData);
      setIsSubmitting(false);
      alert('Профиль успешно обновлен');
      setIsEditing(false);
    } catch (error) {
      setIsSubmitting(false);
      console.error('Ошибка обновления профиля:', error);
      alert('Произошла ошибка при обновлении профиля');
    }
  };

  if (moderationStatus === 'WAIT') {
    return (
      <div className="bg-gradient-to-r from-black to-blue-800 min-h-screen p-10 text-white">
        <div className="max-w-2xl mx-auto p-6  bg-gradient-to-r to-black/50 from-blue-800/90 rounded-md shadow-md">
          <h1 className="text-3xl font-bold text-center mb-6">Профиль ментора</h1>
          <p>Ваш профиль на модерации. Пожалуйста, подождите.</p>
        </div>
      </div>
    );
  }

  if (moderationStatus === 'CANCELED') {
    return (
      <div className="bg-gradient-to-r from-black to-blue-800 min-h-screen p-10 text-white">
        <div className="max-w-2xl mx-auto p-6 bg-black/50 rounded-md shadow-md">
          <h1 className="text-3xl font-bold text-center mb-6">Профиль отменен</h1>
          <p>Ваш профиль был отменен. Пожалуйста, зарегистрируйтесь заново.</p>
        </div>
      </div>
    );
  }

  // Основное отображение профиля, если статус ACTIVE
  if (moderationStatus === 'ACTIVE') {
    return (
      <div className="bg-gradient-to-r from-black to-blue-800 min-h-screen p-10 text-white">
        <div className="w-[90%] mx-auto">
          <h1 className="text-3xl font-bold text-center mb-8">Профиль ментора</h1>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Левая колонка: Аватар и базовые действия */}
            <div className="md:col-span-1 flex flex-col gap-4">
              <div className="bg-black/70 p-4 rounded-md shadow-md flex flex-col items-center">
                {profile.photo ? (
                  <img
                    src={profile.photo}
                    alt={`${profile.name}'s avatar`}
                    className="w-32 h-32 object-cover rounded-full"
                  />
                ) : (
                  <div className="w-32 h-32 rounded-full bg-gray-500 flex items-center justify-center">
                    <span className="text-xl">No Avatar</span>
                  </div>
                )}
                {isEditing && (
                  <input
                    type="file"
                    accept="image/*"
                    onChange={handlePhotoChange}
                    className="mt-4 w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                  />
                )}
              </div>
              <button
                onClick={toggleEdit}
                className="bg-bgButton hover:bg-bgButton/70 text-white py-2 px-4 rounded-md shadow-md transition-colors"
              >
                {isEditing ? 'Отмена' : 'Редактировать профиль'}
              </button>
              {isEditing && (
                <button
                  onClick={handleSubmit}
                  disabled={isSubmitting}
                  className="bg-green-600 hover:bg-green-700 text-white py-2 px-4 rounded-md shadow-md transition-colors"
                >
                  {isSubmitting ? 'Сохраняем...' : 'Сохранить изменения'}
                </button>
              )}
            </div>

            {/* Правая колонка: Детальная информация */}
            <div className="md:col-span-2 flex flex-col gap-6">
              {/* Карточка с личной информацией */}
              <div className="bg-black/70 p-6 rounded-md shadow-md">
                <h2 className="text-2xl font-bold mb-4">Личная информация</h2>
                <div className="mb-4">
                  <h3 className="text-xl font-semibold">Имя</h3>
                  {isEditing ? (
                    <input
                      type="text"
                      name="name"
                      value={profile.name || ''}
                      onChange={handleInputChange}
                      className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                    />
                  ) : (
                    <p>{profile.name}</p>
                  )}
                </div>
                <div className="mb-4">
                  <h3 className="text-xl font-semibold">Должность</h3>
                  {isEditing ? (
                    <input
                      type="text"
                      name="jobTitle"
                      value={profile.jobTitle || ''}
                      onChange={handleInputChange}
                      className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                    />
                  ) : (
                    <p>{profile.jobTitle}</p>
                  )}
                </div>
                <div>
                  <h3 className="text-xl font-semibold">О себе</h3>
                  {isEditing ? (
                    <textarea
                      name="bio"
                      value={profile.bio || ''}
                      onChange={handleInputChange}
                      className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                    />
                  ) : (
                    <p>{profile.bio}</p>
                  )}
                </div>
              </div>

              {/* Карточка со ссылками */}
              <div className="bg-black/70 p-6 rounded-md shadow-md">
                <h2 className="text-2xl font-bold mb-4">Ссылки</h2>
                <div className="mb-4">
                  <h3 className="text-xl font-semibold">LinkedIn</h3>
                  {isEditing ? (
                    <input
                      type="url"
                      name="linkedin"
                      value={profile.linkedin || ''}
                      onChange={handleInputChange}
                      className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                    />
                  ) : (
                    profile.linkedin && (
                      <a
                        href={profile.linkedin}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-blue-400 hover:underline"
                      >
                        {profile.linkedin}
                      </a>
                    )
                  )}
                </div>
                <div>
                  <h3 className="text-xl font-semibold">GitHub</h3>
                  {isEditing ? (
                    <input
                      type="url"
                      name="github"
                      value={profile.github || ''}
                      onChange={handleInputChange}
                      className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                    />
                  ) : (
                    profile.github && (
                      <a
                        href={profile.github}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-blue-400 hover:underline"
                      >
                        {profile.github}
                      </a>
                    )
                  )}
                </div>
              </div>

              {/* Карточка с навыками */}
              <div className="bg-black/70 p-6 rounded-md shadow-md">
                <h2 className="text-2xl font-bold mb-4">Навыки</h2>
                {isEditing ? (
                  <textarea
                    name="skills"
                    value={profile.skills ? profile.skills.join(', ') : ''}
                    onChange={handleInputChange}
                    className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                  />
                ) : (
                  <ul className="flex flex-wrap gap-2">
                    {profile.stack && Array.isArray(profile.stack) ? (
                      profile.stack.map((skill, index) => (
                        <li key={index} className="bg-gray-700 px-3 py-1 rounded">
                          {skill}
                        </li>
                      ))
                    ) : (
                      <li>Нет навыков</li>
                    )}
                  </ul>
                )}
              </div>

              {/* Карточка с образованием */}
              <div className="bg-black/70 p-6 rounded-md shadow-md">
                <h2 className="text-2xl font-bold mb-4">Образование</h2>
                {isEditing ? (
                  <textarea
                    name="education"
                    value={profile.education ? profile.education.join(', ') : ''}
                    onChange={handleInputChange}
                    className="w-full p-2 border border-gray-300 rounded-md bg-gray-800 text-white"
                  />
                ) : (
                  <ul className="list-disc list-inside">
                    {profile.education && Array.isArray(profile.education) ? (
                      profile.education.map((edu, index) => (
                        <li key={index} className="text-lg">{edu}</li>
                      ))
                    ) : (
                      <li>Нет информации об образовании</li>
                    )}
                  </ul>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return null;
};

export default MentorProfile;
