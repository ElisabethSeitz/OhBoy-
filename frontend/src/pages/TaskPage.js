import React, { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';

export default function TaskPage() {
  const { monsterId } = useParams();
  const { monster, tasks, editStatus } = useTasksByMonsterId(monsterId);
  const [status, setStatus] = useState('OPEN');
  const [balance, setBalance] = useState(
    monster?.scoreDoneTasks - monster?.payoutDoneRewards
  );
  const [score, setScore] = useState(monster?.scoreDoneTasks);

  const filteredTasks = tasks?.filter((task) => task.status === status);

  const countTasks = filteredTasks.length;

  return !monster ? null : (
    <>
      <>
        <p>{countTasks}</p>
        <p>tasks</p>
      </>
      <img src={monster.image} alt="monster" />
      <DisplayBalanceOrScore />
      <h3>{monster.name}</h3>
      <button onClick={handleOnClickOPEN}>open</button>
      <button onClick={handleOnClickDONE}>done</button>
      <TaskList
        tasks={filteredTasks}
        monsterId={monsterId}
        editStatus={editStatus}
        updateBalanceAndScore={updateBalanceAndScore}
      />
      <Link to={'/monsters/' + monsterId + '/tasks/create'}>add</Link>
    </>
  );

  function handleOnClickOPEN() {
    setStatus('OPEN');
  }

  function handleOnClickDONE() {
    setStatus('DONE');
  }

  function updateBalanceAndScore(scoreTask) {
    setBalance(balance + scoreTask);
    setScore(score + scoreTask);
  }

  function DisplayBalanceOrScore() {
    if (status === 'OPEN') {
      return (
        <>
          <p>{balance}</p>
          <p>balance</p>
        </>
      );
    }
    return (
      <>
        <p>{score}</p>
        <p>score</p>
      </>
    );
  }
}
