import { useHistory, useParams } from 'react-router-dom';
import React from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardForm from '../forms/RewardForm';

export default function EditRewardPage() {
  const { monsterId, rewardId } = useParams();
  const { edit, rewards, remove, monster } = useRewardsByMonsterId(monsterId);
  const history = useHistory();
  const reward = rewards?.find((reward) => reward.id === rewardId);

  return !reward ? null : (
    <>
      <h5>edit this task</h5>
      <img src={monster.image} alt="monster" />
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
