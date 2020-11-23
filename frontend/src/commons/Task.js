import React from 'react';

export default function Task({ task }) {
  return (
    <div>
      <p>{task.description}</p>
      <p>{task.score}</p>
      <p>{task.status}</p>
    </div>
  );
}
