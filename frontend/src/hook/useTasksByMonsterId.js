import {
  addTask,
  getTasksByMonsterId,
  removeTask,
  updateTask,
} from '../service/TaskService';
import UserContext from '../contexts/UserContext';
import MonsterContext from '../contexts/MonsterContext';
import { useContext, useEffect, useState } from 'react';

export default function useTasksByMonsterId(monsterId) {
  const { monsters } = useContext(MonsterContext);
  const { token, tokenIsValid } = useContext(UserContext);
  const [tasks, setTasks] = useState([]);

  const monster = monsters.find((monster) => monster.id === monsterId);

  useEffect(() => {
    tokenIsValid() &&
      getTasksByMonsterId(token, monsterId).then(setTasks).catch(console.log);
  }, [token, tokenIsValid, monsterId]);

  const create = (description, score) =>
    addTask(description, score, token, monsterId)
      .then((addedTask) => setTasks([...tasks, addedTask]))
      .catch(console.log);

  const edit = (taskId, description, score) => {
    updateTask(taskId, description, score, monsterId, token)
      .then((updatedTask) => {
        const newState = tasks.map((task) =>
          task.id === updatedTask.id ? updatedTask : task
        );
        setTasks(newState);
      })
      .catch(console.log);
  };

  const remove = (taskId) =>
    removeTask(taskId, monsterId, token)
      .then(() => setTasks(tasks.filter((task) => task.id !== taskId)))
      .catch(console.log);

  return { monster, tasks, create, edit, remove };
}
