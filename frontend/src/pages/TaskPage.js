import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';

export default function TaskPage() {
  const { monsterId } = useParams();
  const { refresh, monsters } = useContext(MonsterContext);
  const [status, setStatus] = useState('OPEN');

  const [monster, setMonster] = useState();
  const { tasksFilter, editStatus } = useTasksByMonsterId(monsterId);
  const [filteredTasks, setFilteredTasks] = useState([]);

  useEffect(() => {
    setMonster(monsters.find((m) => m.id === monsterId));
    tasksFilter(status, true).then(setFilteredTasks);
    // eslint-disable-next-line
  }, [monsters, monsterId]);

  useEffect(() => {
    tasksFilter(status, false).then(setFilteredTasks);
    // eslint-disable-next-line
  }, [status]);

  return !monster ? null : (
    <>
      <>
        <Header
          currentMonsterId={monsterId}
          task={true}
          icons={true}
          add={true}
        />
        <p>{filteredTasks.length}</p>
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
        editStatus={editTaskStatus}
      />
    </>
  );

  function handleOnClickOPEN() {
    setStatus('OPEN');
  }

  function handleOnClickDONE() {
    setStatus('DONE');
  }

  async function editTaskStatus(taskId) {
    await editStatus(taskId);
    refresh();
  }

  function DisplayBalanceOrScore() {
    if (status === 'OPEN') {
      return (
        <>
          <p>
            {monster ? monster.scoreDoneTasks - monster.payoutDoneRewards : ''}
          </p>
          <p>balance</p>
        </>
      );
    }
    return (
      <>
        <p>{monster?.scoreDoneTasks}</p>
        <p>score</p>
      </>
    );
  }
}
