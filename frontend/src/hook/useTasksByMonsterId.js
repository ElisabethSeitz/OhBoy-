import {
  addTask,
  getTasksByMonsterId,
  removeTask,
  updateStatus,
  updateTask,
} from '../service/TaskService';
import UserContext from '../contexts/UserContext';
import MonsterContext from '../contexts/MonsterContext';
import { useContext, useEffect, useState } from 'react';

export default function useTasksByMonsterId(monsterId) {
  const { token, tokenIsValid } = useContext(UserContext);
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    tokenIsValid() &&
      getTasksByMonsterId(token, monsterId).then(setTasks).catch(console.log);
  }, [monsterId, tokenIsValid]);

  const tasksFilter = async (status, reloadTasks) => {
    let tasksToFilter = tasks;
    if (reloadTasks) {
      tasksToFilter = await getTasksByMonsterId(token, monsterId);
      setTasks(tasksToFilter);
    }
    return tasksToFilter.filter((task) => task.status === status);
  };

  const create = (description, score) =>
    addTask(description, score, token, monsterId)
      .then((addedTask) => setTasks([...tasks, addedTask]))
      .catch(console.log);

  const edit = (taskId, description, score) =>
    updateTask(taskId, description, score, monsterId, token)
      .then((updatedTask) => {
        const newState = tasks.map((task) =>
          task.id === updatedTask.id ? updatedTask : task
        );
        setTasks(newState);
      })
      .catch(console.log);

  const editStatus = (taskId) =>
    updateStatus(taskId, monsterId, token)
      .then((updatedTask) => {
        const newState = tasks.map((task) =>
          task.id === updatedTask.id ? updatedTask : task
        );
        setTasks(newState);
      })
      .catch(console.log);

  const remove = (taskId) =>
    removeTask(taskId, monsterId, token)
      .then(() => setTasks(tasks.filter((task) => task.id !== taskId)))
      .catch(console.log);

  return {
    tasks,
    tasksFilter,
    create,
    edit,
    remove,
    editStatus,
  };
}
