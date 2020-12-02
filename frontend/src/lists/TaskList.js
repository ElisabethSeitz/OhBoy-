import React from 'react';
import Task from '../commons/Task';
import List from '../components/List';

export default function TaskList({ tasks, monsterId, editStatus }) {
  return (
    <List>
      {tasks?.map((task) => (
        <li key={task.id}>
          <Task task={task} monsterId={monsterId} editStatus={editStatus} />
        </li>
      ))}
    </List>
  );
}
