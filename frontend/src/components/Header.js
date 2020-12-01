import React from 'react';
import MonsterContext from '../contexts/MonsterContext';
import styled from 'styled-components/macro';
import { useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { BsStar, BsCheck, BsFillPlusCircleFill } from 'react-icons/bs';

export default function Header({
  icons,
  currentMonsterId,
  task,
  monster,
  add,
}) {
  const { monsters } = useContext(MonsterContext);
  const history = useHistory();

  const monstersToDisplay = monsters?.filter(
    (monster) => monster.id !== currentMonsterId
  );

  const LinkTasksOrRewards = (task, monsterId) => {
    if (task) {
      return () => history.push('/monsters/' + monsterId + '/tasks');
    }
    return () => history.push('/monsters/' + monsterId + '/rewards');
  };

  return (
    <HeaderStyled>
      <HeadingStyled onClick={() => history.push('/monsters')}>
        OhBoy!
      </HeadingStyled>
      <DisplayMonsterImages />
      <DisplayTaskOrRewardIcon />
      <DisplayAdd />
    </HeaderStyled>
  );

  function DisplayMonsterImages() {
    if (icons) {
      return (
        <ImageContainer>
          {monstersToDisplay.map((monster) => (
            <div key={monster.id}>
              <HeaderMonsterImage
                src={monster.image}
                alt={monster.name}
                onClick={LinkTasksOrRewards(task, monster.id)}
              />
            </div>
          ))}
        </ImageContainer>
      );
    }
    return <div></div>;
  }

  function DisplayTaskOrRewardIcon() {
    if (icons) {
      return TaskOrRewardIcon();
    }
    return <div></div>;
  }

  function TaskOrRewardIcon() {
    if (task) {
      return (
        <BsStarStyled onClick={LinkTasksOrRewards(false, currentMonsterId)} />
      );
    }
    return (
      <BsCheckStyled onClick={LinkTasksOrRewards(true, currentMonsterId)} />
    );
  }

  function DisplayAdd() {
    if (add) {
      return (
        <BsFillPlusCircleFillStyled onClick={createAddLink(currentMonsterId)} />
      );
    }
    return <div></div>;
  }

  function createAddLink(monsterId) {
    if (monster) {
      return () => history.push('/monsters/create');
    }
    if (task) {
      return () => history.push('/monsters/' + monsterId + '/tasks/create');
    } else
      return () => history.push('/monsters/' + monsterId + '/rewards/create');
  }
}

const HeaderStyled = styled.header`
  display: grid;
  grid-template-columns: 20% 50% 15% 15%;
  align-items: center;
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
  width: 30px;
  height: 30px;
  margin-right: var(--size-s);
`;

const ImageContainer = styled.div`
  justify-self: end;
  display: flex;
`;

const BsCheckStyled = styled(BsCheck)`
  justify-self: end;
  width: 25px;
  height: 25px;
`;

const BsStarStyled = styled(BsStar)`
  justify-self: end;
  width: 20px;
  height: 20px;
`;

const BsFillPlusCircleFillStyled = styled(BsFillPlusCircleFill)`
  justify-self: end;
  width: 25px;
  height: 25px;
`;
