import React, { useContext, useState } from 'react';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory } from 'react-router-dom';

const initialState = {
  name: '',
};

export default function MonsterForm({ monster = initialState }) {
  const [monsterData, setMonsterData] = useState(monster);
  const { create } = useContext(MonsterContext);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <label>
        name
        <input
          name="name"
          value={monsterData.name}
          onChange={handleChange}
          type="text"
        />
      </label>
      <button type="button" onClick={handleSave}>
        Save
      </button>
      <button type="button" onClick={handleCancel}>
        Cancel
      </button>
    </form>
  );

  function handleSubmit(event) {
    event.preventDefault();
    handleSave(monsterData);
  }

  function handleChange(event) {
    setMonsterData({ ...monsterData, [event.target.name]: event.target.value });
  }

  function handleSave(monster) {
    const { name } = monster;
    create(name);
    history.push('/monsters');
  }

  function handleCancel() {
    history.goBack();
  }
}
