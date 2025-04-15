import React, { useState } from 'react';
import Header from '../components/Home/Header';

export default function AdminConsole() {
  const [moderators, setModerators] = useState([
    { id: 1, name: 'Иван Иванов', pass: 'ivaxamplem' },
    { id: 2, name: 'Мария Петрова', pass: 'marexampcom' },
  ]);

  const [newModerator, setNewModerator] = useState({ name: '', pass: '' });

  const handleAddModerator = () => {
    const id = moderators.length ? moderators[moderators.length - 1].id + 1 : 1;
    setModerators([...moderators, { id, ...newModerator }]);
    setNewModerator({ name: '', pass: '' });
  };

  const handleDeleteModerator = (id) => {
    setModerators(moderators.filter(mod => mod.id !== id));
  };

  return (
    <div className="bg-gradient-to-b from-black to-blue-800 min-h-screen text-white">
      <Header />
      <div className="pt-24 px-4 md:px-8">
        <h1 className="text-3xl md:text-4xl font-bold mb-8 text-center">
          Управление модераторами
        </h1>
        {/* Раздел: Существующие модераторы */}
        <div className="mb-12">
          <h2 className="text-2xl font-semibold mb-4">Список модераторов</h2>
          <div className="overflow-x-auto">
            <table className="min-w-full bg-black/70 rounded-lg shadow-lg">
              <thead>
                <tr>
                  <th className="py-3 px-4 text-left font-semibold">Имя</th>
                  <th className="py-3 px-4 text-left font-semibold">Пароль</th>
                  <th className="py-3 px-4 text-left font-semibold">Действия</th>
                </tr>
              </thead>
              <tbody>
                {moderators.map(mod => (
                  <tr key={mod.id} className="border-t border-gray-600">
                    <td className="py-4 px-4">{mod.name}</td>
                    <td className="py-4 px-4">{mod.pass}</td>
                    <td className="py-4 px-4">
                      <button
                        onClick={() => handleDeleteModerator(mod.id)}
                        className="w-full md:w-auto border border-white hover:bg-red-700/70 text-white py-2 px-6 rounded transition-colors duration-200 text-lg cursor-pointer"
                      >
                        Удалить
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div>
          <h2 className="text-2xl font-semibold mb-4">Добавить нового модератора</h2>
          <div className="overflow-x-auto">
            <table className="min-w-full bg-black/70 rounded-lg shadow-lg">
              <thead>
                <tr>
                  <th className="py-3 px-4 text-left font-semibold">Имя</th>
                  <th className="py-3 px-4 text-left font-semibold">Пароль</th>
                  <th className="py-3 px-4 text-left font-semibold">Действия</th>
                </tr>
              </thead>
              <tbody>
                <tr className="border-t border-gray-600">
                  <td className="py-4 px-4">
                    <input
                      type="text"
                      placeholder="Введите имя"
                      value={newModerator.name}
                      onChange={(e) => setNewModerator({ ...newModerator, name: e.target.value })}
                      className="w-full bg-transparent border-b border-gray-500 focus:outline-none text-white placeholder-gray-400"
                    />
                  </td>
                  <td className="py-4 px-4">
                    <input
                      type="password"
                      placeholder="Введите пароль"
                      value={newModerator.email}
                      onChange={(e) => setNewModerator({ ...newModerator, pass: e.target.value })}
                      className="w-full bg-transparent border-b border-gray-500 focus:outline-none text-white placeholder-gray-400"
                    />
                  </td>
                  <td className="py-4 px-4">
                    <button
                      onClick={handleAddModerator}
                      className="w-full md:w-auto border border-white hover:bg-green-700/70 text-white py-2 px-6 rounded transition-colors duration-200 text-lg cursor-pointer"
                    >
                      Добавить
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
