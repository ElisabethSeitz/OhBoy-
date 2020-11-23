import React, { useContext, useEffect, useState } from 'react';
import TaskContext from './TaskContext';
import { getTasksByMonsterId } from '../service/TaskService';
import UserContext from './UserContext';

export default function TaskContextProvider({ children, monsterId }) {
  const [tasks, setTasks] = useState([]);
  const { token, tokenIsValid } = useContext(UserContext);

  useEffect(() => {
    tokenIsValid() &&
      getTasksByMonsterId(token, monsterId).then(setTasks).catch(console.log);
  }, [token, tokenIsValid, monsterId]);

  return (
    <TaskContext.Provider value={{ tasks }}>{children}</TaskContext.Provider>
  );
}
