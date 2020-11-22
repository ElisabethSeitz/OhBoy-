import React from 'react';
import MonsterList from '../lists/MonsterList';
import { Link } from 'react-router-dom';

export default function MonsterPage() {
  return (
    <>
      <h1>Monsters</h1>
      <MonsterList />
      <Link to="/monsters/create">add</Link>
    </>
  );
}
