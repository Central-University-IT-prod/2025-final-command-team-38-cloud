import React, { useState } from 'react';
import Header from '../components/Home/Header';

export default function AddModerator() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleAddModerator = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('/api/admin/add-moderator', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });
      if (response.ok) {
        // Handle successful addition
        console.log('Moderator added successfully');
      } else {
        console.error('Failed to add moderator');
      }
    } catch (error) {
      console.error('Error adding moderator:', error);
    }
  };

  return (
    
    <form onSubmit={handleAddModerator}>
      <Header/>
      <h1>Add Moderator</h1>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Add Moderator</button>
    </form>
  );
}
