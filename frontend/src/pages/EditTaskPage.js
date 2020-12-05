import React, { useContext } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import useTasksByMonsterId from '../hook/useTasksByMonsterId';
import TaskForm from '../forms/TaskForm';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSectionSmall from '../components/MonsterSectionSmall';

export default function EditTaskPage() {
  const { monsterId, taskId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { edit, tasks, remove } = useTasksByMonsterId(monsterId);
  const history = useHistory();

  const task = tasks?.find((task) => task.id === taskId);
  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !task ? null : (
    <>
      <Header displayedMonsterId={monsterId} itemType="task" icons={true} />
      <MonsterSectionSmall monster={monster} task={true} add={false} />
      <div>
        <TaskForm onSave={handleSave} task={task} />
        <button type="button" onClick={handleDelete}>
          Delete
        </button>
      </div>
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
