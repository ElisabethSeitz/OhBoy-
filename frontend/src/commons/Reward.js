import { Link } from 'react-router-dom';
import React from 'react';

export default function Reward({ reward, monsterId, editStatus }) {
  function DisplayEditButton() {
    const statusOpen = reward.status === 'OPEN';
    if (statusOpen) {
      return (
        <Link to={'/monsters/' + monsterId + '/rewards/edit/' + reward.id}>
          edit
        </Link>
      );
    }
    return null;
  }

  function DisplayChangeStatusButton() {
    const statusOpen = reward.status === 'OPEN';
    if (statusOpen) {
      return <button onClick={handleClick}>done</button>;
    }
    return <button onClick={handleClick}>open</button>;
  }

  return (
    <div>
      <p>{reward.description}</p>
      <p>{reward.score}</p>
      <p>{reward.status}</p>
      <DisplayEditButton />
      <DisplayChangeStatusButton />
    </div>
  );

  function handleClick() {
    editStatus(reward.id);
  }
}
