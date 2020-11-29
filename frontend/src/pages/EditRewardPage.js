import { useHistory, useParams } from 'react-router-dom';
import React, { useContext } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardForm from '../forms/RewardForm';
import MonsterContext from '../contexts/MonsterContext';

export default function EditRewardPage() {
  const { monsterId, rewardId } = useParams();
  const { monsters } = useContext(MonsterContext);
  const { edit, rewards, remove } = useRewardsByMonsterId(monsterId);
  const history = useHistory();

  const reward = rewards?.find((reward) => reward.id === rewardId);
  const monster = monsters?.find((monster) => monster.id === monsterId);

  return !reward ? null : (
    <>
      <h5>edit this reward</h5>
      <img src={monster?.image} alt="monster" />
      <RewardForm onSave={handleSave} reward={reward} />
      <button type="button" onClick={handleDelete}>
        Delete
      </button>
    </>
  );

  async function handleSave(description, score) {
    await edit(reward.id, description, score);
    history.push('/monsters/' + monsterId + '/rewards');
  }

  async function handleDelete() {
    await remove(reward.id);
    history.push('/monsters/' + monsterId + '/rewards');
  }
}
