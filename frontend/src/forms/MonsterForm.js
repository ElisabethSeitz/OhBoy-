import React, { useState } from 'react';
import MonsterGallery from './MonsterGallery';
import { useHistory } from 'react-router-dom';

const initialState = {
  name: '',
};

export default function MonsterForm({ onSave, monster = initialState }) {
  const [monsterData, setMonsterData] = useState(monster);
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
      <button>Save</button>
      <button type="button" onClick={handleCancel}>
        Cancel
      </button>
    </form>
  );

  function handleSubmit(event) {
    event.preventDefault();
    const imageSrc = window.document.getElementById('currentImage').src;
    const imageSrcToBeSaved = imageSrc.slice(-27);
    onSave(monsterData, imageSrcToBeSaved);
  }

  function handleChange(event) {
    setMonsterData({ ...monsterData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.goBack();
  }
}
