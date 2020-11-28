import { useContext, useEffect, useState } from 'react';
import MonsterContext from '../contexts/MonsterContext';
import UserContext from '../contexts/UserContext';
import {
  addReward,
  getRewardsByMonsterId,
  removeReward,
  updateReward,
  updateStatus,
} from '../service/RewardService';

export default function useRewardsByMonsterId(monsterId) {
  const { monsters } = useContext(MonsterContext);
  const { token, tokenIsValid } = useContext(UserContext);
  const [rewards, setRewards] = useState([]);

  const monster = monsters.find((monster) => monster.id === monsterId);

  useEffect(() => {
    tokenIsValid() &&
      getRewardsByMonsterId(token, monsterId)
        .then(setRewards)
        .catch(console.log);
  }, [token, tokenIsValid, monsterId]);

  const create = (description, score) =>
    addReward(description, score, token, monsterId)
      .then((addedReward) => setRewards([...rewards, addedReward]))
      .catch(console.log);

  const edit = (rewardId, description, score) => {
    updateReward(rewardId, description, score, monsterId, token)
      .then((updatedReward) => {
        const newState = rewards.map((reward) =>
          reward.id === updatedReward.id ? updatedReward : reward
        );
        setRewards(newState);
      })
      .catch(console.log);
  };

  const editStatus = (rewardId) => {
    updateStatus(rewardId, monsterId, token)
      .then((updatedReward) => {
        const newState = rewards.map((reward) =>
          reward.id === updatedReward.id ? updatedReward : reward
        );
        setRewards(newState);
      })
      .catch(console.log);
  };

  const remove = (rewardId) =>
    removeReward(rewardId, monsterId, token)
      .then(() =>
        setRewards(rewards.filter((reward) => reward.id !== rewardId))
      )
      .catch(console.log);

  return { monster, rewards, create, edit, remove, editStatus };
}
