import MonsterContext from '../contexts/MonsterContext';
import React, { useContext } from 'react';
import Monster from '../commons/Monster';
import List from '../components/List';
import styled from 'styled-components/macro';

export default function MonsterList() {
  const { monsters } = useContext(MonsterContext);

  return (
    <ListStyled>
      {monsters?.map((monster) => (
        <li key={monster.id}>
          <Monster monster={monster} />
        </li>
      ))}
    </ListStyled>
  );
}

const ListStyled = styled(List)`
  padding-top: var(--size-xxl);
`;
