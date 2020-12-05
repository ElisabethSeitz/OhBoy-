import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSection from '../components/MonsterSection';
import OpenDoneSwitch from '../components/OpenDoneSwitch';
import AddButton from '../components/AddButton';

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
      <Header displayedMonsterId={monsterId} itemType="task" icons={true} />
      <div>
        <MonsterSection
          monster={monster}
          filteredItems={filteredTasks}
          status={status}
          task={true}
        />
        <OpenDoneSwitch
          handleOnClickDONE={handleOnClickDONE}
          handleOnClickOPEN={handleOnClickOPEN}
        />
      </div>
      <TaskList
        tasks={filteredTasks}
        monsterId={monsterId}
        editStatus={editTaskStatus}
      />
      <AddButton monster={false} itemType="task" currentMonsterId={monsterId} />
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
}
