import React from 'react';
import styled from 'styled-components/macro';

export default function MonsterSectionSmall({ monster, task, add }) {
  return (
    <MonsterSectionSmallStyled>
      <img className="monsterImage" src={monster?.image} alt={monster?.name} />
      <DisplayTaskOrReward />
    </MonsterSectionSmallStyled>
  );

  function DisplayTaskOrReward() {
    if (add && task) {
      return (
        <p>
          add a task <br /> for {monster.name}
        </p>
      );
    }
    if (add && !task) {
      return (
        <p>
          add a reward <br /> for {monster.name}
        </p>
      );
    }
    if (!add && task) {
      return <p>edit this task</p>;
    }
    if (!add && !task) {
      return <p>edit this reward</p>;
    }
  }
}

const MonsterSectionSmallStyled = styled.section`
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: min-content min-content;
  justify-items: center;
  text-align: center;

  .monsterImage {
    padding-top: var(--size-l);
  }
`;
