import React from 'react';
import MonsterList from '../lists/MonsterList';
import { Link } from 'react-router-dom';

export default function MonsterPage() {
  return (
    <>
      <p>Monsters</p>
      <MonsterList />
      <Link to="/monsters/create">add</Link>
    </>
  );
}
