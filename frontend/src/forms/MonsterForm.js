import React, { useState } from 'react';
import MonsterGallery from './MonsterGallery';
import { useHistory } from 'react-router-dom';
import InputField from '../components/InputField';

const initialState = {
  name: '',
  image: '',
};

export default function MonsterForm({ onSave, monster = initialState }) {
  const [monsterData, setMonsterData] = useState(monster);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <MonsterGallery
        savedMonsterImage={monster.image}
        imageChangedHandler={setMonsterImage}
      />
      <InputField
        name="name"
        placeholder="enter name"
        value={monsterData.name}
        onChange={handleChange}
        type="text"
        required
      >
        name
      </InputField>
      <button>Save</button>
      <button type="button" onClick={handleCancel}>
        Cancel
      </button>
    </form>
  );

  function handleSubmit(event) {
    event.preventDefault();
    onSave(monsterData.name, monsterData.image);
  }

  function setMonsterImage(monsterImage) {
    setMonsterData({ ...monsterData, image: monsterImage });
  }

  function handleChange(event) {
    setMonsterData({ ...monsterData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.goBack();
  }
}
