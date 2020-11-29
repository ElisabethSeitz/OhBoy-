import { useContext, useEffect, useState } from 'react';
import UserContext from '../contexts/UserContext';
import {
  addReward,
  getRewardsByMonsterId,
  removeReward,
  updateReward,
  updateStatus,
} from '../service/RewardService';

export default function useRewardsByMonsterId(monsterId) {
  const { token, tokenIsValid } = useContext(UserContext);
  const [rewards, setRewards] = useState([]);

  useEffect(() => {
    tokenIsValid() &&
      getRewardsByMonsterId(token, monsterId)
        .then(setRewards)
        .catch(console.log);
  }, [monsterId, tokenIsValid, token]);

  const rewardsFilter = async (status, reloadRewards) => {
    let rewardsToFilter = rewards;
    if (reloadRewards) {
      rewardsToFilter = await getRewardsByMonsterId(token, monsterId);
      setRewards(rewardsToFilter);
    }
    return rewardsToFilter.filter((reward) => reward.status === status);
  };

  const create = (description, score) =>
    addReward(description, score, token, monsterId)
      .then((addedReward) => setRewards([...rewards, addedReward]))
      .catch(console.log);

  const edit = (rewardId, description, score) =>
    updateReward(rewardId, description, score, monsterId, token)
      .then((updatedReward) => {
        const newState = rewards.map((reward) =>
          reward.id === updatedReward.id ? updatedReward : reward
        );
        setRewards(newState);
      })
      .catch(console.log);

  const editStatus = (rewardId) =>
    updateStatus(rewardId, monsterId, token)
      .then((updatedReward) => {
        const newState = rewards.map((reward) =>
          reward.id === updatedReward.id ? updatedReward : reward
        );
        setRewards(newState);
      })
      .catch(console.log);

  const remove = (rewardId) =>
    removeReward(rewardId, monsterId, token)
      .then(() =>
        setRewards(rewards.filter((reward) => reward.id !== rewardId))
      )
      .catch(console.log);

  return { rewards, rewardsFilter, create, edit, remove, editStatus };
}
