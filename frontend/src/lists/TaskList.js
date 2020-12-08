import React from 'react';
import List from '../components/List';
import Item from '../commons/Item';

export default function TaskList({ tasks, monsterId, editStatus }) {
  return (
    <List>
      {tasks?.map((task) => (
        <li key={task.id}>
          <Item
            itemType="task"
            item={task}
            monsterId={monsterId}
            editStatus={editStatus}
          />
        </li>
      ))}
    </List>
  );
}
