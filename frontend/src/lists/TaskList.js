import React, { useContext } from 'react';
import TaskContext from '../contexts/TaskContext';
import Task from '../commons/Task';

export default function TaskList() {
  const { tasks } = useContext(TaskContext);

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
