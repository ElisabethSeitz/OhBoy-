import { useHistory, useParams } from 'react-router-dom';
import React from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardForm from '../forms/RewardForm';

export default function AddRewardPage() {
  const { monsterId } = useParams();
  const { monster, create } = useRewardsByMonsterId(monsterId);
  const history = useHistory();

  return !monster ? null : (
    <>
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
