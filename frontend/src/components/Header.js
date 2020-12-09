import React from 'react';
import MonsterContext from '../contexts/MonsterContext';
import styled, { css } from 'styled-components/macro';
import { useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { BsStar, BsCheck } from 'react-icons/bs';
import { BiDoorOpen } from 'react-icons/bi';

export default function Header({
  icons,
  displayedMonsterId,
  itemType,
  onLogout,
}) {
  const { monsters } = useContext(MonsterContext);
  const history = useHistory();

  const LinkTasksOrRewards = (itemType, monsterId) => {
    if (itemType === 'task') {
      return () => history.push('/monsters/' + monsterId + '/tasks');
    }
    if (itemType === 'reward') {
      return () => history.push('/monsters/' + monsterId + '/rewards');
    }
  };

  return (
    <HeaderStyled itemType={itemType}>
      <HeadingStyled onClick={() => history.push('/monsters')}>
        OhKid!
      </HeadingStyled>
      <DisplayMonsterImages />
      <DisplayTaskOrRewardIcon />
      <DisplayLogout />
    </HeaderStyled>
  );

  function DisplayMonsterImages() {
    if (icons) {
      return (
        <ImageContainer>
          {monsters.map((monster) => (
            <MonsterImages
              key={monster.id}
              currentMonsterTask={
                monster.id === displayedMonsterId && itemType === 'task'
              }
              currentMonsterReward={
                monster.id === displayedMonsterId && itemType === 'reward'
              }
            >
              <HeaderMonsterImage
                src={monster.image}
                alt={monster.name}
                onClick={LinkTasksOrRewards(itemType, monster.id)}
              />
            </MonsterImages>
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
    if (itemType === 'task') {
      return (
        <div
          className="items"
          onClick={LinkTasksOrRewards('reward', displayedMonsterId)}
        >
          <BsStarStyled />
          <p className="itemText">rewards</p>
        </div>
      );
    }
    if (itemType === 'reward') {
      return (
        <div
          className="items"
          onClick={LinkTasksOrRewards('task', displayedMonsterId)}
        >
          <BsCheckStyled />
          <p className="itemText">tasks</p>
        </div>
      );
    }
  }

  function DisplayLogout() {
    if (itemType === 'monster') {
      return (
        <div className="logout" onClick={() => onLogout()}>
          <BiDoorOpenStyled />
        </div>
      );
    }
    return null;
  }
}

const HeaderStyled = styled.header`
  display: grid;
  align-items: center;
  grid-template-columns: min-content 4fr 2fr var(--size-xxl);
  padding: var(--size-s) 0;
  margin: 0;
  border-bottom: var(--orange-border);
  background-color: var(--beige-main);
  justify-items: end;

  ${(props) =>
    props.itemType === 'task' &&
    css`
      border-bottom: var(--blue-border);
    `}

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      border-bottom: var(--green-border);
    `}

  .items {
    font-size: var(--size-m);
    color: var(--grey-font);
    grid-column: 3;
    padding: 0;
  }

  .itemText {
    margin: 0;
  }

  .logout {
    grid-row: 1;
    grid-column: 3;
    height: 32px;
  }
`;

const HeadingStyled = styled.h1`
  margin: 0;
  padding: var(--size-xs) var(--size-xl);
  color: black;
  font-size: var(--size-xxl);
  font-family: 'Glass Antiqua';
`;

const HeaderMonsterImage = styled.img`
  width: 30px;
  height: 30px;
  margin: var(--size-xs) var(--size-xs) 0 var(--size-xs);
`;

const ImageContainer = styled.div`
  justify-self: end;
  display: flex;
`;

const MonsterImages = styled.div`
  ${(props) =>
    props.currentMonsterTask &&
    css`
      background-color: rgba(105, 163, 176, 0.1);
      border-radius: 90px;
    `}

  ${(props) =>
    props.currentMonsterReward &&
    css`
      background-color: rgba(168, 206, 0, 0.1);
      border-radius: 90px;
    `}
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

const BiDoorOpenStyled = styled(BiDoorOpen)`
  font-size: var(--size-xxl);
`;
