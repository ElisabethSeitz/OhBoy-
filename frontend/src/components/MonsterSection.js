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
      <p className="countTasks">{filteredItems.length}</p>
      <DisplayTasksOrRewards />
      <img className="monsterImage" src={monster.image} alt={monster.name} />
      <h1 className="monsterName">{monster.name}</h1>
      <DisplayBalanceOrScorePayout />
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
  grid-template-rows: min-content min-content min-content;
  justify-items: center;

  .countTasks {
    grid-column: 2;
    grid-row: 1;
  }

  .tasks {
    grid-column: 2;
    grid-row: 2;
    color: var(--grey-font);
  }

  .rewards {
    grid-column: 2;
    grid-row: 2;
    color: var(--grey-font);
  }

  .calculatedBalance {
    grid-column: 4;
    grid-row: 1;
  }

  .calculatedScore {
    grid-column: 4;
    grid-row: 1;
  }

  .calculatedPayout {
    grid-column: 4;
    grid-row: 1;
  }

  .balance {
    grid-column: 4;
    grid-row: 2;
    color: var(--grey-font);
  }

  .score {
    grid-column: 4;
    grid-row: 2;
    color: var(--grey-font);
  }

  .payout {
    grid-column: 4;
    grid-row: 2;
    color: var(--grey-font);
  }

  .monsterImage {
    grid-column: 3;
    grid-row: span 2;
  }

  .monsterName {
    grid-column: 3;
    grid-row: 3;
    font-size: var(--size-l);
  }
`;
