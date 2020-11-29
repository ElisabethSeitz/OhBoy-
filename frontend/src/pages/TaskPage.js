import React, { useContext, useEffect, useState } from 'react';
import { Link, useParams, useHistory } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';
import Header from '../components/Header';
import MonsterContext from '../contexts/MonsterContext';

export default function TaskPage() {
  const history = useHistory();
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
  }, [monsters]);

  useEffect(() => {
    tasksFilter(status, false).then(setFilteredTasks);
    // eslint-disable-next-line
  }, [status]);

  return !monster ? null : (
    <>
      <>
        <Header currentMonsterId={monsterId} />
        <p>{filteredTasks.length}</p>
        <p>tasks</p>
      </>
      <img
        src={monster.image}
        alt="monster"
        onClick={() => history.push('/monsters')}
      />
      <DisplayBalanceOrScore />
      <h3>{monster.name}</h3>
      <button onClick={handleOnClickOPEN}>open</button>
      <button onClick={handleOnClickDONE}>done</button>
      <TaskList
        tasks={filteredTasks}
        monsterId={monsterId}
        editStatus={editTaskStatus}
      />
      <div>
        <Link to={'/monsters/' + monsterId + '/tasks/create'}>add</Link>
      </div>
      <Link to={'/monsters/' + monsterId + '/rewards'}>rewards</Link>
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
