import React from 'react';
import MonsterList from '../lists/MonsterList';
import Header from '../components/Header';
import AddButton from '../components/AddButton';
import styled from 'styled-components/macro';

export default function MonsterPage() {
  return (
    <MonsterPageLayout>
      <Header icons={false} />
      <MonsterList />
      <AddButton itemType="monster" />
    </MonsterPageLayout>
  );
}

const MonsterPageLayout = styled.div`
  display: grid;
  grid-template-rows: min-content 1fr;
  height: 100vh;
`;
