import React from 'react';
import { Link } from 'react-router-dom';

export default function Task({ task, monsterId }) {
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

  return (
    <div>
      <p>{task.description}</p>
      <p>{task.score}</p>
      <p>{task.status}</p>
      <DisplayEditButton statusOpen={true} />
    </div>
  );
}
