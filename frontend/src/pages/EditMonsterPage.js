import React, { useContext } from 'react';
import MonsterForm from '../forms/MonsterForm';
import MonsterContext from '../contexts/MonsterContext';
import { useHistory, useParams } from 'react-router-dom';
import Confirmation from '../components/Confirmation';
import Header from '../components/Header';

export default function EditMonsterPage() {
  const { edit, monsters, remove } = useContext(MonsterContext);
  const history = useHistory();
  const { id } = useParams();
  const monster = monsters.find((monster) => monster.id === id);

  return !monster ? null : (
    <>
      <Header icons={false} />
      <h5>edit your monster</h5>
      <div>
        <MonsterForm onSave={handleSave} monster={monster} />
        <Confirmation
          onClick={handleDelete}
          label="Delete"
          question="All related tasks and rewards will be deleted as well. Delete?"
        />
      </div>
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
