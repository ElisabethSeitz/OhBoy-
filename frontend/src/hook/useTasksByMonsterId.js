import { getTasksByMonsterId } from '../service/TaskService';
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

  return [monster, tasks];
}
