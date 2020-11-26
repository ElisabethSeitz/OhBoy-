import React from 'react';
import Task from '../commons/Task';

export default function TaskList({ tasks, monsterId }) {
  return (
    <ul>
      {tasks?.map((task) => (
        <li key={task.id}>
          <Task task={task} monsterId={monsterId} />
        </li>
      ))}
    </ul>
  );
}
