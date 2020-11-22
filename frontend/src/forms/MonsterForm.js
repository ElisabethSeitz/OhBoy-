import React, { useContext, useState } from 'react';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory } from 'react-router-dom';
import MonsterGallery from './MonsterGallery';

const initialState = {
  name: '',
};

export default function MonsterForm({ monster = initialState }) {
  const [monsterData, setMonsterData] = useState(monster);
  const { create } = useContext(MonsterContext);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <MonsterGallery />
      <label>
        name
        <input
          name="name"
          value={monsterData.name}
          onChange={handleChange}
          type="text"
          required
        />
      </label>
      <button id="save">Save</button>
      <button type="button" onClick={handleCancel}>
        Cancel
      </button>
    </form>
  );

  function handleSubmit(event) {
    event.preventDefault();
    handleSave(monsterData.name);
  }

  function handleSave() {
    const monsterImage = window.document.getElementById('currentImage').src;
    create(monsterData.name, monsterImage);
    history.push('/monsters');
  }

  function handleChange(event) {
    setMonsterData({ ...monsterData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.goBack();
  }
}
