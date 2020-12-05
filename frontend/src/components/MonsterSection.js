import React from 'react';
import styled from 'styled-components/macro';

export default function MonsterSection({
  monster,
  filteredItems,
  status,
  task,
}) {
  return (
    <MonsterSectionStyled>
      <p className="countItems">{filteredItems.length}</p>
      <DisplayTasksOrRewards />
      <div className="background1" />
      <img className="monsterImage" src={monster.image} alt={monster.name} />
      <p className="monsterName">{monster.name}</p>
      <DisplayBalanceOrScorePayout />
      <div className="background2" />
    </MonsterSectionStyled>
  );

  function DisplayBalanceOrScorePayout() {
    if (status === 'OPEN') {
      return (
        <>
          <p className="calculatedBalance">
            {monster ? monster.scoreDoneTasks - monster.payoutDoneRewards : ''}
          </p>
          <p className="balance">balance</p>
        </>
      );
    }
    if (status !== 'OPEN' && task) {
      return (
        <>
          <p className="calculatedScore">{monster?.scoreDoneTasks}</p>
          <p className="score">score</p>
        </>
      );
    }
    return (
      <>
        <p className="calculatedPayout">{monster?.payoutDoneRewards}</p>
        <p className="payout">payout</p>
      </>
    );
  }

  function DisplayTasksOrRewards() {
    if (task) {
      return <p className="tasks">tasks</p>;
    }
    return <p className="rewards">rewards</p>;
  }
}

const MonsterSectionStyled = styled.section`
  display: grid;
  grid-template-columns: 1fr 1fr 2fr 1fr 1fr;
  grid-template-rows: min-content min-content min-content min-content;
  justify-items: center;

  .countItems {
    margin: 0;
    padding-bottom: var(--size-s);
    grid-column: 2;
    grid-row: 2;
    align-self: end;
    font-weight: 500;
  }

  .tasks,
  .rewards {
    margin: 0;
    grid-column: 2;
    grid-row: 3;
    color: var(--grey-font);
  }

  .calculatedScore,
  .calculatedPayout,
  .calculatedBalance {
    margin: 0;
    padding-bottom: var(--size-s);
    grid-column: 4;
    grid-row: 2;
    align-self: end;
    font-weight: 500;
  }

  .balance {
    margin: 0;
    grid-column: 4;
    grid-row: 3;
    color: var(--grey-font);
  }

  .score,
  .payout {
    margin: 0;
    grid-column: 4;
    grid-row: 3;
    color: var(--grey-font);
  }

  .monsterImage {
    grid-column: 3;
    grid-row: span 3;
    padding-top: var(--size-l);
  }

  .monsterName {
    margin: 0;
    padding-top: var(--size-s);
    padding-bottom: var(--size-xs);
    grid-column: 3;
    grid-row: 4;
    font-size: var(--size-xl);
    font-family: 'Glass Antiqua';
  }

  .background1,
  .background2 {
    height: 90px;
    width: 90px;
    background-color: rgba(105, 163, 176, 0.1);
    grid-column: 2;
    grid-row-start: 2;
    grid-row-end: span 2;
    border-radius: var(--size-s);
  }

  .background2 {
    grid-column: 4;
    grid-row-start: 2;
  }
`;
