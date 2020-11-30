import React from 'react';
import MonsterList from '../lists/MonsterList';
import { Link } from 'react-router-dom';
import Header from '../components/Header';

export default function MonsterPage() {
  return (
    <>
      <Header icons={false} monster={true} add={true} />
      <h1>Monsters</h1>
      <MonsterList />
      <Link to="/monsters/create">add</Link>
    </>
  );
}
