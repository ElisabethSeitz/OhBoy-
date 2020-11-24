import React from 'react';
import Task from '../commons/Task';

export default function TaskList({ tasks, status }) {
  const filteredTasks = tasks.filter((task) => task.status === status);

  return (
    <ul>
      {filteredTasks?.map((task) => (
        <li>
          <Task task={task} />
        </li>
      ))}
    </ul>
  );
}
