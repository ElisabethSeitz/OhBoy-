import React from 'react';
import MonsterContext from '../contexts/MonsterContext';
import styled from 'styled-components/macro';
import { useContext } from 'react';

export default function Header({ currentMonsterId }) {
  const { monsters } = useContext(MonsterContext);

  const monstersToDisplay = monsters?.filter(
    (monster) => monster.id !== currentMonsterId
  );

  return (
    <HeaderStyled>
      <HeadingStyled>OhBoy!</HeadingStyled>
      <ImageContainer>
        {monstersToDisplay.map((monster) => (
          <div key={monster.id}>
            <HeaderMonsterImage src={monster.image} alt={monster.name} />
          </div>
        ))}
      </ImageContainer>
    </HeaderStyled>
  );
}

const HeaderStyled = styled.header`
  display: grid;
  grid-template-columns: 25% 50% 25%;
  grid-template-rows: 1fr;
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

const ImageContainer = styled.div`
  align-self: end;
`;
