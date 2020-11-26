import React from 'react';
import { Link } from 'react-router-dom';

export default function Task({ task, monsterId, editStatus }) {
  function DisplayEditButton() {
    const statusOpen = task.status === 'OPEN';
    if (statusOpen) {
      return (
        <Link to={'/monsters/' + monsterId + '/tasks/edit/' + task.id}>
          edit
        </Link>
      );
    }
    return null;
  }

  function DisplayChangeStatusButton() {
    const statusOpen = task.status === 'OPEN';
    if (statusOpen) {
      return <button onClick={handleClick}>done</button>;
    }
    return <button onClick={handleClick}>open</button>;
  }

  return (
    <div>
      <p>{task.description}</p>
      <p>{task.score}</p>
      <p>{task.status}</p>
      <DisplayEditButton />
      <DisplayChangeStatusButton />
    </div>
  );

  function handleClick() {
    editStatus(task.id);
  }
}
