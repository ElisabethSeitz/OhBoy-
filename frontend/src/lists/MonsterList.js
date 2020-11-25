import MonsterContext from '../contexts/MonsterContext';
import React, { useContext } from 'react';
import Monster from '../commons/Monster';

export default function MonsterList() {
  const { monsters } = useContext(MonsterContext);

  return (
    <ul>
      {monsters?.map((monster) => (
        <li key={monster.id}>
          <Monster monster={monster} />
        </li>
      ))}
    </ul>
  );
}
