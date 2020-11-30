import React from 'react';
import MonsterContext from '../contexts/MonsterContext';
import styled from 'styled-components/macro';
import { useContext } from 'react';
import { useHistory } from 'react-router-dom';

export default function Header({ currentMonsterId, taskId }) {
  const { monsters } = useContext(MonsterContext);
  const history = useHistory();

  const monstersToDisplay = monsters?.filter(
    (monster) => monster.id !== currentMonsterId
  );

  const monsterLink = (taskId, monsterId) => {
    if (taskId) {
      return history.push('/monsters/' + monsterId + '/tasks');
    }
    return history.push('/monsters/' + monsterId + '/rewards');
  };

  return (
    <HeaderStyled>
      <HeadingStyled>OhBoy!</HeadingStyled>
      <div>
        {monstersToDisplay.map((monster) => (
          <div key={monster.id}>
            <HeaderMonsterImage
              src={monster.image}
              alt={monster.name}
              onClick={monsterLink(taskId, monster.id)}
            />
          </div>
        ))}
      </div>
    </HeaderStyled>
  );
}

const HeaderStyled = styled.header`
  display: grid;
  grid-template-columns: 25% 50% 25%;
  padding: var(--size-s) 0;
  margin: 0 var(--size-s);
  border-bottom: var(--black-border);
`;

const HeadingStyled = styled.h1`
  margin: 0;
  padding: var(--size-xs);
  color: black;
  font-size: var(--size-l);
`;

const HeaderMonsterImage = styled.img`
  width: 25px;
  height: 25px;
  float: left;
`;
