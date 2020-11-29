import { Link, useParams, useHistory } from 'react-router-dom';
import React, { useState } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardList from '../lists/RewardList';

export default function RewardPage() {
  const history = useHistory();
  const { monsterId } = useParams();
  const { monster, rewards, editStatus } = useRewardsByMonsterId(monsterId);
  const [status, setStatus] = useState('OPEN');
  const [balance, setBalance] = useState(
    monster?.scoreDoneTasks - monster?.payoutDoneRewards
  );
  const [payout, setPayout] = useState(monster?.payoutDoneRewards);

  const filteredRewards = rewards?.filter((reward) => reward.status === status);

  const countRewards = filteredRewards.length;

  return !monster ? null : (
    <>
      <>
        <p>{countRewards}</p>
        <p>rewards</p>
      </>
      <img
        src={monster.image}
        alt="monster"
        onClick={() => history.push('/monsters')}
      />
      <DisplayBalanceOrPayout />
      <h3>{monster.name}</h3>
      <button onClick={handleOnClickOPEN}>open</button>
      <button onClick={handleOnClickDONE}>done</button>
      <RewardList
        rewards={filteredRewards}
        monsterId={monsterId}
        editStatus={editStatus}
        updateBalanceAndPayout={updateBalanceAndPayout}
      />
      <div>
        <Link to={'/monsters/' + monsterId + '/rewards/create'}>add</Link>
      </div>
      <Link to={'/monsters/' + monsterId + '/tasks'}>tasks</Link>
    </>
  );

  function handleOnClickOPEN() {
    setStatus('OPEN');
  }

  function handleOnClickDONE() {
    setStatus('DONE');
  }

  function updateBalanceAndPayout(scoreReward) {
    setBalance(balance - scoreReward);
    setPayout(payout + scoreReward);
  }

  function DisplayBalanceOrPayout() {
    if (status === 'OPEN') {
      return (
        <>
          <p>{balance}</p>
          <p>balance</p>
        </>
      );
    }
    return (
      <>
        <p>{payout}</p>
        <p>payout</p>
      </>
    );
  }
}
