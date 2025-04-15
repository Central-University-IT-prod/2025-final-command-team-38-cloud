  import React, { useState, useEffect } from 'react';

  export const RegistStudent = ({ setVisible }) => {
    const [formData, setFormData] = useState({
      fullName: '',
      experience: 'no', // Default value
      techStack: '',
      telegramId: '',
    });

    useEffect(() => {
      // Check if student data already exists in local storage
      const storedStudentData = localStorage.getItem('studentData');
      if (!storedStudentData) {
        setVisible(true); // Show the form if student data does not exist
      } else {
        setVisible(false); // Hide the form if student data exists
      }
    }, [setVisible]);

    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData({
        ...formData,
        [name]: value,
      });
    };

    const handleSubmit = (e) => {
      e.preventDefault();

      // Validate Telegram ID
      const telegramIdPattern = /^@\w{5,}$/; // Ensure ID starts with "@" and has at least 5 characters
      if (!telegramIdPattern.test(formData.telegramId)) {
        alert('Пожалуйста, введите корректный ID Telegram (должен начинаться с "@" и содержать минимум 5 символов).');
        return;
      }

      // Save student data to local storage
      localStorage.setItem('studentData', JSON.stringify(formData));
      setVisible(false);
      console.log('Данные студента:', formData);
    };

    return (
      <form 
        onSubmit={handleSubmit} 
        className="p-4 rounded-lg shadow-lg w-full max-w-md space-y-4 bg-gray-900 text-white"
      >
        <h2 className="text-2xl font-bold text-center text-white mb-4">Регистрация студента</h2>

        <div>
          <label className="block mb-1 text-white">Имя и фамилия:</label>
          <input
            type="text"
            name="fullName"
            value={formData.fullName}
            onChange={handleChange}
            required
            className="border border-gray-300 p-2 w-full rounded"
          />
        </div>

        <div>
          <label className="block mb-1 text-white">Опыт:</label>
          <select
            name="experience"
            value={formData.experience}
            onChange={handleChange}
            required
            className="border border-gray-300 p-2 w-full rounded text-gray-200"
          >
            <option value="no" className="bg-gray-900">Без опыта</option>
            <option value="yes" className="bg-gray-900">С опытом</option>
          </select>
        </div>

        {formData.experience === 'yes' && (
          <div>
            <label className="block mb-1 text-white">Стек технологий:</label>
            <input
              type="text"
              name="techStack"
              value={formData.techStack}
              onChange={handleChange}
              required
              className="border border-gray-300 p-2 w-full rounded"
            />
          </div>
        )}

        <div>
          <label className="block mb-1 text-white">ID Telegram:</label>
          <input
            type="text"
            name="telegramId"
            value={formData.telegramId}
            onChange={handleChange}
            required
            className="border border-gray-300 p-2 w-full rounded"
          />
        </div>

        <button 
          type="submit" 
          className="bg-[#1d742c] text-white px-4 py-2 rounded hover:bg-[#78c981] transition duration-200"
        >
         Зарегаться
        </button>
      </form>
    );
  };

