import React from 'react';
import MonsterList from '../lists/MonsterList';
import Header from '../components/Header';

export default function MonsterPage() {
  return (
    <>
      <Header icons={false} monster={true} add={true} />
      <MonsterList />
    </>
  );
}
