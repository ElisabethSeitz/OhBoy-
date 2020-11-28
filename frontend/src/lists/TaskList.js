import React from 'react';
import Task from '../commons/Task';

export default function TaskList({
  tasks,
  monsterId,
  editStatus,
  updateBalanceAndScore,
}) {
  return (
    <ul>
      {tasks?.map((task) => (
        <li key={task.id}>
          <Task
            task={task}
            monsterId={monsterId}
            editStatus={editStatus}
            updateBalanceAndScore={updateBalanceAndScore}
          />
        </li>
      ))}
    </ul>
  );
}
