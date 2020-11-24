import React, { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';

export default function TaskPage() {
  const { monsterId } = useParams();
  const [monster, tasks] = useTasksByMonsterId(monsterId);
  const [status, setStatus] = useState('OPEN');

  const countTasks =
    status === 'OPEN' ? monster?.countOpenTasks : monster?.countDoneTasks;

  return !monster ? null : (
    <>
      <>
        <p>{countTasks}</p>
        <p>tasks</p>
      </>
      <img src={monster.image} alt="monster" />
      <>
        <p>{monster.balance}</p>
        <p>balance</p>
      </>
      <h3>{monster.name}</h3>
      <button onClick={handleOnClickOPEN}>open</button>{' '}
      <button onClick={handleOnClickDONE}>done</button>
      <TaskList tasks={tasks} status={status} />
      <Link>add</Link>
    </>
  );

  function handleOnClickOPEN() {
    setStatus('OPEN');
  }

  function handleOnClickDONE() {
    setStatus('DONE');
  }
}
