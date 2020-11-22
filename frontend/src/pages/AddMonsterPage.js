import React, { useContext } from 'react';
import MonsterForm from '../forms/MonsterForm';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory } from 'react-router-dom';

export default function AddMonsterPage() {
  const { create } = useContext(MonsterContext);
  const history = useHistory();

  return (
    <>
      <h5>add your monster</h5>
      <MonsterForm onSave={handleSave} />
    </>
  );

  function handleSave(monsterName, monsterImage) {
    create(monsterName, monsterImage);
    history.push('/monsters');
  }
}
