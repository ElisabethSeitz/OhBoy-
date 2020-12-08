import { useHistory, useParams } from 'react-router-dom';
import React, { useContext } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardForm from '../forms/RewardForm';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSectionSmall from '../components/MonsterSectionSmall';

export default function AddRewardPage() {
  const { monsterId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { create } = useRewardsByMonsterId(monsterId);
  const history = useHistory();

  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !monster ? null : (
    <>
      <Header displayedMonsterId={monsterId} itemType="reward" icons={true} />
      <MonsterSectionSmall
        monster={monster}
        itemType="reward"
        actionType="create"
      />
      <RewardForm onSave={handleSave} monsterId={monsterId} />
    </>
  );

  async function handleSave(description, score) {
    await create(description, score);
    history.push('/monsters/' + monsterId + '/rewards');
  }
}
