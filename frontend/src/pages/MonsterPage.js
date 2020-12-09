import React, { useContext } from 'react';
import MonsterList from '../lists/MonsterList';
import Header from '../components/Header';
import AddButton from '../components/AddButton';
import styled from 'styled-components/macro';
import { useHistory } from 'react-router-dom';
import UserContext from '../contexts/UserContext';

export default function MonsterPage() {
  const { logoutFacebook } = useContext(UserContext);
  const history = useHistory();

  return (
    <MonsterPageLayout>
      <Header icons={false} itemType="monster" onLogout={handleLogout} />
      <MonsterList />
      <AddButton itemType="monster" />
    </MonsterPageLayout>
  );

  function handleLogout() {
    logoutFacebook();
    history.push('/login');
  }
}

const MonsterPageLayout = styled.div`
  display: grid;
  grid-template-rows: min-content 1fr;
  height: 100vh;
`;
