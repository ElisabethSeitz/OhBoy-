import React from 'react';
import { useHistory, useParams } from 'react-router-dom';
import useTasksByMonsterId from '../hook/useTasksByMonsterId';
import TaskForm from '../forms/TaskForm';

export default function EditTaskPage() {
  const { monsterId, taskId } = useParams();
  const { edit, tasks, remove } = useTasksByMonsterId(monsterId);
  const history = useHistory();
  const task = tasks?.find((task) => task.id === taskId);

  return !task ? null : (
    <>
      <h5>edit this task</h5>
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
