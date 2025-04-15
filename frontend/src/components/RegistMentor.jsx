import React, { useState, useEffect } from "react";
import Header from "./Home/Header";
import axios from "axios";
import { stackData } from "../hooks/stackData";
import { useNavigate } from "react-router-dom";

const resourceOptions = [
  "GitHub",
  "LinkedIn",
  "Twitter",
  "YouTube",
];

const RegistMentor = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    age: "",
    stack: [],
    email: "",
    telegram: "",
    resources: [],
    bio: "",
    experience: "",
    costPerHour: "",
    active: true,
    photo: "",
  });

  const [errors, setErrors] = useState({});
  const [stackSearch, setStackSearch] = useState("");
  const [showResourceDropdown, setShowResourceDropdown] = useState(false);

  // Redirect if mentor already exists
  useEffect(() => {
    const idMentor = localStorage.getItem("idMentor");
    if (idMentor) {
      navigate("/profile");
    }
  }, [navigate]);

  // Validate single field
  const validateField = (name, value) => {
    switch (name) {
      case "firstName":
      case "lastName":
        if (!value.trim()) return "Обязательное поле.";
        if (value.trim().length < 2) return "Минимум 2 символа.";
        if (value.trim().length > 30) return "Максимум 30 символов.";
        return "";
      case "age": {
        const ageNum = parseInt(value, 10);
        if (isNaN(ageNum)) return "Введите число.";
        if (ageNum < 16 || ageNum > 100)
          return "Возраст должен быть от 16 до 100.";
        return "";
      }
      case "email": {
        if (!value.trim()) return "Обязательное поле.";
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) return "Некорректный email.";
        return "";
      }
      case "telegram":
        if (value.trim() && !value.startsWith("@"))
          return "Строка должна начинаться с символа '@'.";
        return "";
      case "bio":
        if (!value.trim()) return "Обязательное поле.";
        if (value.trim().length < 150)
          return "Минимум 150 символов в описании.";
        if (value.trim().length > 1500)
          return "Максимум 1500 символов в описании.";
        return "";
      case "experience": {
        const expNum = parseInt(value, 10);
        if (isNaN(expNum)) return "Введите число.";
        if (expNum < 0 || expNum > 100)
          return "Опыт должен быть от 0 до 100.";
        return "";
      }
      case "costPerHour": {
        const cost = parseInt(value, 10);
        if (isNaN(cost)) return "Введите число.";
        if (cost < 0) return "Стоимость не может быть отрицательной.";
        return "";
      }
      case "photo":
        if (value && value.length > 206400)
          return "Максимальная длина превышена.";
        return "";
      default:
        return "";
    }
  };

  // Validate entire form
  const validateForm = () => {
    const newErrors = {};

    // Validate simple fields
    Object.keys(formData).forEach((field) => {
      if (["stack", "resources"].includes(field)) return;
      const error = validateField(field, formData[field]);
      if (error) newErrors[field] = error;
    });

    // Validate stack array (each element must be non-blank and length 2-50)
    if (formData.stack.length === 0) {
      newErrors.stack = "Выберите хотя бы один элемент стека.";
    } else {
      formData.stack.forEach((tech) => {
        if (!tech.trim()) newErrors.stack = "Элемент не может быть пустым.";
        if (tech.trim().length < 2)
          newErrors.stack = "Минимум 2 символа для элемента стека.";
        if (tech.trim().length > 50)
          newErrors.stack = "Максимум 50 символов для элемента стека.";
      });
    }

    // Validate resources: если выбраны, ссылка не должна быть пустой
    formData.resources.forEach((resource, index) => {
      if (!resource.url.trim()) {
        newErrors[`resource_${index}`] = "Ссылка обязательна для ресурса.";
      }
    });

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: validateField(name, value) }));
  };

  // Handle form submit
  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      postData(formData);
    }
  };

  // Post data to backend
  const postData = async (data) => {
    try {
      const response = await axios.post(
        `${import.meta.env.VITE_BACKEND_BASE_URL}/mentor`,
        {
          ...data,
          resources: data.resources.map((resource) => resource.url),
        },
        {
          headers: {
            Authorization: "application",
          },
        }
      );
      localStorage.setItem("idMentor", response.data.id);
      navigate("/profile");
    } catch (error) {
      console.error("Error registering mentor:", error);
      alert("Произошла ошибка при регистрации ментора.");
    }
  };

  // Handle stack technology selection
  const filteredStack = stackData.filter((tech) =>
    tech.toLowerCase().startsWith(stackSearch.toLowerCase())
  );

  const handleStackSelection = (tech) => {
    if (!formData.stack.includes(tech)) {
      setFormData((prev) => ({ ...prev, stack: [...prev.stack, tech] }));
    }
    setStackSearch("");
    // Remove potential stack errors on selection
    setErrors((prev) => ({ ...prev, stack: "" }));
  };

  const removeStackItem = (tech) => {
    setFormData((prev) => ({
      ...prev,
      stack: prev.stack.filter((item) => item !== tech),
    }));
  };

  // Handle resource selection
  const handleResourceSelection = (resource) => {
    const exists = formData.resources.find((r) => r.type === resource);
    if (exists) {
      setFormData((prev) => ({
        ...prev,
        resources: prev.resources.filter((r) => r.type !== resource),
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        resources: [...prev.resources, { type: resource, url: "" }],
      }));
    }
  };

  const handleResourceLinkChange = (resourceType, newUrl) => {
    setFormData((prev) => ({
      ...prev,
      resources: prev.resources.map((r) =>
        r.type === resourceType ? { ...r, url: newUrl } : r
      ),
    }));
    // Clear error for this resource if link is provided
    setErrors((prev) => ({ ...prev, [`resource_${resourceType}`]: "" }));
  };

  return (
    <div className="bg-gradient-to-b from-black to-blue-800 p-10 text-white">
      <Header />
      <div className="max-w-2xl mx-auto p-6 bg-gradient-to-b from-blue-500/50 to-black shadow-md rounded-md border-white">
        <h1 className="text-3xl font-bold text-center mb-6">Регистрация ментора</h1>
        <form onSubmit={handleSubmit}>
          {/* First Name */}
          <div className="mb-4">
            <label htmlFor="firstName" className="block text-lg font-medium">
              Имя
            </label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.firstName && (
              <p className="text-red-500 text-sm mt-1">{errors.firstName}</p>
            )}
          </div>

          {/* Last Name */}
          <div className="mb-4">
            <label htmlFor="lastName" className="block text-lg font-medium">
              Фамилия
            </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.lastName && (
              <p className="text-red-500 text-sm mt-1">{errors.lastName}</p>
            )}
          </div>

          {/* Age */}
          <div className="mb-4">
            <label htmlFor="age" className="block text-lg font-medium">
              Возраст
            </label>
            <input
              type="number"
              id="age"
              name="age"
              value={formData.age}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.age && (
              <p className="text-red-500 text-sm mt-1">{errors.age}</p>
            )}
          </div>

          {/* Stack */}
          <div className="mb-4">
            <label className="block text-lg font-medium">Стек</label>
            <input
              type="text"
              placeholder="Начните вводить технологию..."
              value={stackSearch}
              onChange={(e) => setStackSearch(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-md mb-2"
            />
            {stackSearch && filteredStack.length > 0 && (
              <div className="max-h-60 overflow-y-auto border border-gray-300 rounded-md bg-white shadow-lg">
                {filteredStack.map((tech) => (
                  <div
                    key={tech}
                    onClick={() => handleStackSelection(tech)}
                    className="px-3 py-2 hover:bg-gray-200 cursor-pointer"
                  >
                    {tech}
                  </div>
                ))}
              </div>
            )}
            {formData.stack.length > 0 && (
              <div className="flex flex-wrap gap-2 mt-2">
                {formData.stack.map((tech) => (
                  <div
                    key={tech}
                    className="flex items-center px-3 py-1 rounded-full bg-blue-500 text-white"
                  >
                    <span>{tech}</span>
                    <button
                      type="button"
                      onClick={() => removeStackItem(tech)}
                      className="ml-2 text-white hover:text-red-300"
                    >
                      &times;
                    </button>
                  </div>
                ))}
              </div>
            )}
            {errors.stack && (
              <p className="text-red-500 text-sm mt-1">{errors.stack}</p>
            )}
          </div>

          {/* Email */}
          <div className="mb-4">
            <label htmlFor="email" className="block text-lg font-medium">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.email && (
              <p className="text-red-500 text-sm mt-1">{errors.email}</p>
            )}
          </div>

          {/* Telegram */}
          <div className="mb-4">
            <label htmlFor="telegram" className="block text-lg font-medium">
              Telegram
            </label>
            <input
              type="text"
              id="telegram"
              name="telegram"
              value={formData.telegram}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
            />
            {errors.telegram && (
              <p className="text-red-500 text-sm mt-1">{errors.telegram}</p>
            )}
          </div>

          {/* Resources */}
          <div className="mb-4">
            <label className="block text-lg font-medium">Ресурсы</label>
            <button
              type="button"
              onClick={() => setShowResourceDropdown(!showResourceDropdown)}
              className="w-full p-3 border border-gray-300 rounded-md text-white bg-transparent hover:bg-blue-400 hover:border-blue-400"
            >
              {showResourceDropdown ? "Скрыть ресурсы" : "Выбрать ресурсы"}
            </button>
            {showResourceDropdown && (
              <div className="mt-2 border border-gray-300 rounded-md bg-white shadow-lg">
                {resourceOptions.map((resource) => (
                  <div
                    key={resource}
                    onClick={() => handleResourceSelection(resource)}
                    className={`px-3 py-2 hover:bg-gray-200 cursor-pointer ${
                      formData.resources.find((r) => r.type === resource)
                        ? "bg-blue-500 text-white"
                        : ""
                    }`}
                  >
                    {resource}
                  </div>
                ))}
              </div>
            )}
            {formData.resources.length > 0 && (
              <div className="mt-2">
                {formData.resources.map((resourceObj, index) => (
                  <div key={resourceObj.type} className="flex flex-col gap-1 mt-2">
                    <div className="flex items-center">
                      <span className="flex-1 px-3 py-1 rounded-full bg-blue-500 text-white">
                        {resourceObj.type}
                      </span>
                      <button
                        type="button"
                        onClick={() => handleResourceSelection(resourceObj.type)}
                        className="ml-2 text-white hover:text-red-300"
                      >
                        &times;
                      </button>
                    </div>
                    <input
                      type="text"
                      placeholder={`Введите ссылку для ${resourceObj.type}`}
                      value={resourceObj.url}
                      onChange={(e) =>
                        handleResourceLinkChange(resourceObj.type, e.target.value)
                      }
                      className="w-full p-2 border border-gray-300 rounded-md"
                    />
                    {errors[`resource_${index}`] && (
                      <p className="text-red-500 text-sm mt-1">
                        {errors[`resource_${index}`]}
                      </p>
                    )}
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Bio */}
          <div className="mb-4">
            <label htmlFor="bio" className="block text-lg font-medium">
              О себе
            </label>
            <textarea
              id="bio"
              name="bio"
              value={formData.bio}
              onChange={handleChange}
              rows="4"
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.bio && (
              <p className="text-red-500 text-sm mt-1">{errors.bio}</p>
            )}
          </div>

          {/* Experience */}
          <div className="mb-4">
            <label htmlFor="experience" className="block text-lg font-medium">
              Опыт (лет)
            </label>
            <input
              type="number"
              id="experience"
              name="experience"
              value={formData.experience}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.experience && (
              <p className="text-red-500 text-sm mt-1">{errors.experience}</p>
            )}
          </div>

          {/* Cost per Hour */}
          <div className="mb-4">
            <label htmlFor="costPerHour" className="block text-lg font-medium">
              Тариф (₽/час)
            </label>
            <input
              type="number"
              id="costPerHour"
              name="costPerHour"
              value={formData.costPerHour}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
              required
            />
            {errors.costPerHour && (
              <p className="text-red-500 text-sm mt-1">{errors.costPerHour}</p>
            )}
          </div>

          {/* Photo */}
          <div className="mb-4">
            <label htmlFor="photo" className="block text-lg font-medium">
              Фото (URL)
            </label>
            <input
              type="text"
              id="photo"
              name="photo"
              value={formData.photo}
              onChange={handleChange}
              className="w-full p-3 border border-gray-300 rounded-md"
            />
            {errors.photo && (
              <p className="text-red-500 text-sm mt-1">{errors.photo}</p>
            )}
          </div>

          <button
            type="submit"
            className="w-full p-3 bg-bgButton text-white font-bold rounded-md hover:bg-bgButton/70"
          >
            Зарегистрировать
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegistMentor;
