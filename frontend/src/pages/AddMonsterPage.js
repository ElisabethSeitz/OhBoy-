import React, { useContext } from 'react';
import MonsterForm from '../forms/MonsterForm';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory } from 'react-router-dom';
import Header from '../components/Header';

export default function AddMonsterPage() {
  const { create } = useContext(MonsterContext);
  const history = useHistory();

  return (
    <>
      <Header icons={false} />
      <MonsterForm onSave={handleSave} actionType="create" />
    </>
  );

  function handleSave(monsterName, monsterImage) {
    create(monsterName, monsterImage);
    history.push('/monsters');
  }
}
