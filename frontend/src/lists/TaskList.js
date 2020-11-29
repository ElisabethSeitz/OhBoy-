import React from 'react';
import Task from '../commons/Task';

export default function TaskList({ tasks, monsterId, editStatus }) {
  return (
    <ul>
      {tasks?.map((task) => (
        <li key={task.id}>
          <Task task={task} monsterId={monsterId} editStatus={editStatus} />
        </li>
      ))}
    </ul>
  );
}
