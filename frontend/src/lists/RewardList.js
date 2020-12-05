import React from 'react';
import List from '../components/List';
import Item from '../commons/Item';

export default function RewardList({ rewards, monsterId, editStatus }) {
  return (
    <List>
      {rewards?.map((reward) => (
        <li key={reward.id}>
          <Item
            itemType="reward"
            item={reward}
            monsterId={monsterId}
            editStatus={editStatus}
          />
        </li>
      ))}
    </List>
  );
}
