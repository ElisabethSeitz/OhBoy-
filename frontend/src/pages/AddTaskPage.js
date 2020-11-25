import React from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TaskForm from '../forms/TaskForm';
import useTasksByMonsterId from '../hook/useTasksByMonsterId';

export default function AddTaskPage() {
  const { monsterId } = useParams();
  const { monster, create } = useTasksByMonsterId(monsterId);
  const history = useHistory();

  return !monster ? null : (
    <>
      <h5>add a task for {monster.name}</h5>
      <img src={monster.image} alt="monster" />
      <TaskForm onSave={handleSave} />
    </>
  );

  async function handleSave(description, score) {
    await create(description, score);
    history.push('/monsters/' + monsterId + '/tasks');
  }
}
