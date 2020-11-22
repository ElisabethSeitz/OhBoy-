import { getMonstersByUserId, addMonster } from '../service/MonsterService';
import UserContext from './UserContext';
import React, { useContext, useEffect, useState } from 'react';
import MonsterContext from './MonsterContext';

export default function MonsterContextProvider({ children }) {
  const [monsters, setMonsters] = useState([]);
  const { token, tokenIsValid, userData } = useContext(UserContext);

  useEffect(() => {
    tokenIsValid() &&
      getMonstersByUserId(token, userData.sub)
        .then(setMonsters)
        .catch(console.log);
  }, [token, tokenIsValid]);

  const create = (name, image) =>
    addMonster(token, name, userData.sub, image)
      .then((addedMonster) => setMonsters([...monsters, addedMonster]))
      .catch(console.log);

  return (
    <MonsterContext.Provider value={{ monsters, create }}>
      {children}
    </MonsterContext.Provider>
  );
}
