import React, { useContext, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import TaskContext from '../contexts/TaskContext';
import TaskList from '../lists/TaskList';
import TaskContextProvider from '../contexts/TaskContextProvider';
import MonsterContext from '../contexts/MonsterContext';

export default function TaskPage() {
  const { monsters } = useContext(MonsterContext);
  const { monsterId } = useParams();

  const monster = monsters.find((monster) => monster.id === monsterId);

  return (
    <>
      <TaskContextProvider monsterId={monsterId}>
        <section>
          <h1>{monster.name}</h1>
          <img src={monster.image} alt="monster" />
          <label>task</label>
          <label>balance</label>
          <button>open</button> <button>done</button>
        </section>
        <TaskList />
      </TaskContextProvider>
      <Link to="monsters/tasks/create">add</Link>
    </>
  );
}
