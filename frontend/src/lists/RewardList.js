import React from 'react';
import Reward from '../commons/Reward';
import List from '../components/List';

export default function RewardList({ rewards, monsterId, editStatus }) {
  return (
    <List>
      {rewards?.map((reward) => (
        <li key={reward.id}>
          <Reward
            reward={reward}
            monsterId={monsterId}
            editStatus={editStatus}
          />
        </li>
      ))}
    </List>
  );
}
