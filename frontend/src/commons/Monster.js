import React from 'react';
import { Link } from 'react-router-dom';

export default function Monster({ monster }) {
  return (
    <div>
      <p>{monster.name}</p>
      <img src={monster.image} alt="monster image" />
      <Link to={'/monsters/edit/' + monster.id}>edit</Link>
    </div>
  );
}
