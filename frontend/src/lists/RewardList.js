import React from 'react';
import Reward from '../commons/Reward';

export default function RewardList({
  rewards,
  monsterId,
  editStatus,
  updateBalanceAndPayout,
}) {
  return (
    <ul>
      {rewards?.map((reward) => (
        <li key={reward.id}>
          <Reward
            reward={reward}
            monsterId={monsterId}
            editStatus={editStatus}
            updateBalanceAndPayout={updateBalanceAndPayout}
          />
        </li>
      ))}
    </ul>
  );
}
