import React from 'react';
import MonsterContext from '../contexts/MonsterContext';
import styled from 'styled-components/macro';
import { useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { BsStar, BsCheck } from 'react-icons/bs';

export default function Header({ icons, currentMonsterId, task }) {
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
        OhKid!
      </HeadingStyled>
      <DisplayMonsterImages />
      <DisplayTaskOrRewardIcon />
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
        <div className="rewards">
          <BsStarStyled onClick={LinkTasksOrRewards(false, currentMonsterId)} />
          <p className="rewardsText">rewards</p>
        </div>
      );
    }
    return (
      <div className="tasks">
        <BsCheckStyled onClick={LinkTasksOrRewards(true, currentMonsterId)} />
        <p className="tasksText">tasks</p>
      </div>
    );
  }
}

const HeaderStyled = styled.header`
  display: grid;
  align-items: center;
  grid-template-columns: min-content 4fr 2fr var(--size-xxl);
  padding: var(--size-s) 0;
  margin: 0;
  border-bottom: var(--blue-border);
  background-color: var(--beige-main);
  justify-items: end;

  .rewards {
    font-size: var(--size-m);
    color: var(--grey-font);
    grid-column: 3;
    margin: 0 0 0 var(--size-xxl);
    padding: 0;
  }

  .rewardsText {
    margin: 0;
  }

  .tasks {
    font-size: var(--size-m);
    color: var(--grey-font);
    grid-column: 3;
    padding: 0;
  }

  .tasksText {
    margin: 0;
  }
`;

const HeadingStyled = styled.h1`
  margin: 0;
  padding: var(--size-xs) var(--size-xl);
  color: black;
  font-size: var(--size-xl);
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
  width: 15px;
  height: 15px;
  color: var(--grey-font);
  margin: 0 0 0 var(--size-s);
`;

const BsStarStyled = styled(BsStar)`
  width: 15px;
  height: 15px;
  color: var(--grey-font);
  margin: 0 0 0 var(--size-m);
`;
