import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import mentorData from "../pages/mentorProfile.json";

const MentorProfile = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [profile, setProfile] = useState(mentorData[0]); // Assuming mentorData is an array

  // Function to toggle editing mode
  const toggleEdit = () => setIsEditing((prev) => !prev);

  // Function to handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProfile((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Function to handle form submission to send updated data to the server
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Send the updated profile data to the server
      await axios.put("/api/mentor/update", profile); // Replace with actual API endpoint
      alert("Profile updated successfully!");
      setIsEditing(false);
    } catch (error) {
      console.error("Error updating profile:", error);
      alert("Error updating profile!");
    }
  };

  return (
    <div className="bg-gradient-to-r from-black to-blue-800 p-10 text-white">
      <div className="max-w-2xl mx-auto p-6 bg-black/50 shadow-md rounded-md border-white border-1">
        <h1 className="text-3xl font-bold text-center mb-6">Профиль ментора</h1>

        {/* Avatar */}
        <div className="flex justify-center mb-6">
          {profile.avatar ? (
            <img
              src={profile.photo}
              alt={`${profile.name}'s avatar`}
              className="w-32 h-32 rounded-full shadow-md "
            />
          ) : (
            <div className="w-32 h-32 rounded-full bg-gray-500 flex items-center justify-center">
              <span className="text-xl">No Avatar</span>
            </div>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">Имя</h2>
          {isEditing ? (
            <input
              type="text"
              name="name"
              value={profile.name}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <p>{profile.name}</p>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">Должность</h2>
          {isEditing ? (
            <input
              type="text"
              name="jobTitle"
              value={profile.jobTitle}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <p>{profile.jobTitle}</p>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">О себе</h2>
          {isEditing ? (
            <textarea
              name="bio"
              value={profile.bio}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <p>{profile.bio}</p>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">LinkedIn</h2>
          {isEditing ? (
            <input
              type="url"
              name="linkedin"
              value={profile.linkedin}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <a href={profile.linkedin} target="_blank" rel="noopener noreferrer" className="text-blue-500">
              {profile.linkedin}
            </a>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">GitHub</h2>
          {isEditing ? (
            <input
              type="url"
              name="github"
              value={profile.github}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <a href={profile.github} target="_blank" rel="noopener noreferrer" className="text-blue-500">
              {profile.github}
            </a>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">Навыки</h2>
          {isEditing ? (
            <textarea
              name="skills"
              value={profile.skills.join(", ")}
              onChange={(e) => handleInputChange(e)}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <ul className="list-disc list-inside">
              {profile.skills.map((skill, index) => (
                <li key={index} className="text-lg">{skill}</li>
              ))}
            </ul>
          )}
        </div>

        <div className="mb-4">
          <h2 className="text-xl font-semibold">Образование</h2>
          {isEditing ? (
            <textarea
              name="education"
              value={profile.education.join(", ")}
              onChange={(e) => handleInputChange(e)}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          ) : (
            <ul className="list-disc list-inside">
              {profile.education.map((edu, index) => (
                <li key={index} className="text-lg">{edu}</li>
              ))}
            </ul>
          )}
        </div>

        <div className="mt-6">
          {isEditing ? (
            <button
              onClick={handleSubmit}
              className="w-full p-3 bg-bgButton text-white font-bold rounded-md hover:bg-bgButton/70"
            >
              Сохранить изменения
            </button>
          ) : (
            <button
              onClick={toggleEdit}
              className="w-full p-3 bg-bgButton text-white font-bold rounded-md hover:bg-bgButton/70"
            >
              Редактировать профиль
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default MentorProfile;
