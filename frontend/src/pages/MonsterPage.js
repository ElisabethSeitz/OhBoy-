import React from 'react';
import MonsterList from '../lists/MonsterList';
import Header from '../components/Header';
import AddButton from '../components/AddButton';

export default function MonsterPage() {
  return (
    <>
      <Header icons={false} monster={true} />
      <div> </div>
      <MonsterList />
      <AddButton monster={true} />
    </>
  );
}
