import {
  getMonstersByUserId,
  addMonster,
  updateMonster,
  removeMonster,
} from '../service/MonsterService';
import UserContext from './UserContext';
import React, { useContext, useEffect, useState } from 'react';
import MonsterContext from './MonsterContext';

export default function MonsterContextProvider({ children }) {
  const [monsters, setMonsters] = useState([]);
  const { token, tokenIsValid } = useContext(UserContext);

  useEffect(() => {
    tokenIsValid() &&
      getMonstersByUserId(token).then(setMonsters).catch(console.log);
  }, [token, tokenIsValid]);

  const create = (name, image) =>
    addMonster(token, name, image)
      .then((addedMonster) => setMonsters([...monsters, addedMonster]))
      .catch(console.log);

  const edit = (monsterId, name, image) => {
    updateMonster(monsterId, name, image, token)
      .then((updatedMonster) => {
        const newState = monsters.map((monster) =>
          monster.id === updatedMonster.id ? updatedMonster : monster
        );
        setMonsters(newState);
      })
      .catch(console.log);
  };

  const remove = (monsterId) =>
    removeMonster(monsterId, token)
      .then(() =>
        setMonsters(monsters.filter((monster) => monster.id !== monsterId))
      )
      .catch(console.log);

  const refresh = () =>
    getMonstersByUserId(token).then(setMonsters).catch(console.log);

  return (
    <MonsterContext.Provider
      value={{ monsters, create, edit, remove, refresh }}
    >
      {children}
    </MonsterContext.Provider>
  );
}
