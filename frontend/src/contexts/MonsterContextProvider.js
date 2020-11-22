import {
  getMonstersByUserId,
  addMonster,
  updateMonster,
} from '../service/MonsterService';
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
  }, [token, tokenIsValid, userData]);

  const create = (name, image) =>
    addMonster(token, name, userData.sub, image)
      .then((addedMonster) => setMonsters([...monsters, addedMonster]))
      .catch(console.log);

  const edit = (monsterId, name, image) => {
    updateMonster(monsterId, userData.sub, name, image, token)
      .then((updatedMonster) => {
        const newState = [...monsters];
        const updatedMonsterIndex = newState.findIndex(
          (monster) => monster.id === updatedMonster.id
        );
        newState.splice(updatedMonsterIndex, 1, updatedMonster);
        setMonsters(newState);
      })
      .catch(console.log);
  };

  return (
    <MonsterContext.Provider value={{ monsters, create, edit }}>
      {children}
    </MonsterContext.Provider>
  );
}
