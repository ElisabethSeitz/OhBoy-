import React from 'react';
import { Link, useParams } from 'react-router-dom';
import TaskList from '../lists/TaskList';
import useTasksByMonsterId from '../hook/useTasksByMonsterId.js';

export default function TaskPage() {
  const { monsterId } = useParams();
  const [monster, tasks] = useTasksByMonsterId(monsterId);

  return !monster ? null : (
    <>
      <section>
        <h1>{monster.name}</h1>
        <img src={monster.image} alt="monster" />
        <label>task</label>
        <label>balance</label>
        <button>open</button> <button>done</button>
      </section>
      <TaskList tasks={tasks} />

      <Link to="monsters/tasks/create">add</Link>
    </>
  );
}
