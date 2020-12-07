import React, { useContext } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TaskForm from '../forms/TaskForm';
import useTasksByMonsterId from '../hook/useTasksByMonsterId';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSectionSmall from '../components/MonsterSectionSmall';

export default function AddTaskPage() {
  const { monsterId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { create } = useTasksByMonsterId(monsterId);
  const history = useHistory();

  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !monster ? null : (
    <>
      <Header displayedMonsterId={monsterId} itemType="task" icons={true} />
      <MonsterSectionSmall
        monster={monster}
        itemType="task"
        actionType="create"
      />
      <TaskForm onSave={handleSave} />
    </>
  );

  async function handleSave(description, score) {
    await create(description, score);
    history.push('/monsters/' + monsterId + '/tasks');
  }
}
