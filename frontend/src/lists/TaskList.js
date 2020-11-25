import React from 'react';
import Task from '../commons/Task';

export default function TaskList({ tasks }) {
  return (
    <ul>
      {tasks?.map((task) => (
        <li>
          <Task task={task} />
        </li>
      ))}
    </ul>
  );
}
