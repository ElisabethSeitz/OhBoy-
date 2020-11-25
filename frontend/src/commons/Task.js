import React from 'react';
import { Link } from 'react-router-dom';

export default function Task({ task, monsterId }) {
  return (
    <div>
      <p>{task.description}</p>
      <p>{task.score}</p>
      <p>{task.status}</p>
      <Link to={'/monsters/' + monsterId + '/tasks/edit/' + task.id}>edit</Link>
    </div>
  );
}
