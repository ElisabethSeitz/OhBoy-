import React from 'react';

export default function Monster({ monster }) {
  return (
    <div>
      <p>{monster.name}</p>
      <img src={monster.image} alt="monster image" />
    </div>
  );
}
