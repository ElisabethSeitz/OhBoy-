import MonsterContext from '../contexts/MonsterContext';
import styled from 'styled-components/macro';
import { useContext } from 'react';

export default function Header({ currentMonsterId }) {
  const { monsters } = useContext(MonsterContext);

  const monstersToDisplay = monsters?.filter(
    (monster) => monster.id != currentMonsterId
  );

  return (
    <HeaderStyled>
      <HeadingStyled>OhBoy!</HeadingStyled>
    </HeaderStyled>
  );
}

const HeaderStyled = styled.header`
  display: flex;
  justify-content: stretch;
  align-items: stretch;
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
