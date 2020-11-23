import React from 'react';
import { Link, useHistory } from 'react-router-dom';

export default function Monster({ monster }) {
  const history = useHistory();

  return (
    <div>
      <p onClick={handleClick}>{monster.name}</p>
      <img src={monster.image} alt="monster" />
      <Link to={'/monsters/edit/' + monster.id}>edit</Link>
    </div>
  );

  function handleClick() {
    history.push('/monsters/tasks/' + monster.id);
  }
}
