import { useHistory, useParams } from 'react-router-dom';
import React, { useContext } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardForm from '../forms/RewardForm';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';

export default function AddRewardPage() {
  const { monsterId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { create } = useRewardsByMonsterId(monsterId);
  const history = useHistory();

  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !monster ? null : (
    <>
      <Header
        currentMonsterId={monsterId}
        task={false}
        icons={true}
        add={false}
      />
      <h5>add a reward for {monster.name}</h5>
      <img src={monster.image} alt="monster" />
      <RewardForm onSave={handleSave} />
    </>
  );

  async function handleSave(description, score) {
    await create(description, score);
    history.push('/monsters/' + monsterId + '/rewards');
  }
}
