import React, { useContext } from 'react';
import MonsterForm from '../forms/MonsterForm';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory, useParams } from 'react-router-dom';

export default function EditMonsterPage() {
  const { edit, monsters, remove } = useContext(MonsterContext);
  const history = useHistory();
  const { id } = useParams();
  const monster = monsters.find((monster) => monster.id === id);

  return !monster ? null : (
    <>
      <h5>edit your monster</h5>
      <MonsterForm onSave={handleSave} monster={monster} />
      <button type="button" onClick={handleDelete}>
        Delete
      </button>
    </>
  );

  function handleSave(monsterName, monsterImage) {
    edit(monster.id, monsterName, monsterImage);
    history.push('/monsters');
  }

  function handleDelete() {
    remove(monster.id);
    history.push('/monsters');
  }
}
