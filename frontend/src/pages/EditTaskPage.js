import React, { useContext } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import useTasksByMonsterId from '../hook/useTasksByMonsterId';
import TaskForm from '../forms/TaskForm';
import MonsterContext from '../contexts/MonsterContext';

export default function EditTaskPage() {
  const { monsterId, taskId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { edit, tasks, remove } = useTasksByMonsterId(monsterId);
  const history = useHistory();

  const task = tasks?.find((task) => task.id === taskId);
  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !task ? null : (
    <>
      <h5>edit this task</h5>
      <img src={monster?.image} alt="monster" />
      <TaskForm onSave={handleSave} task={task} />
      <button type="button" onClick={handleDelete}>
        Delete
      </button>
    </>
  );

  async function handleSave(description, score) {
    await edit(task.id, description, score);
    history.push('/monsters/' + monsterId + '/tasks');
  }

  async function handleDelete() {
    await remove(task.id);
    history.push('/monsters/' + monsterId + '/tasks');
  }
}
