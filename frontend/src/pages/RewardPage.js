import { Link, useParams, useHistory } from 'react-router-dom';
import React, { useContext, useEffect, useState } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardList from '../lists/RewardList';
import MonsterContext from '../contexts/MonsterContext';

export default function RewardPage() {
  const history = useHistory();
  const { monsterId } = useParams();
  const { refresh, monsters } = useContext(MonsterContext);
  const [status, setStatus] = useState('OPEN');

  const [monster, setMonster] = useState();
  const { rewardsFilter, editStatus } = useRewardsByMonsterId(monsterId);
  const [filteredRewards, setFilteredRewards] = useState([]);

  useEffect(() => {
    setMonster(monsters.find((m) => m.id === monsterId));
    rewardsFilter(status, true).then(setFilteredRewards);
    // eslint-disable-next-line
  }, [monsters]);

  useEffect(() => {
    rewardsFilter(status, false).then(setFilteredRewards);
    // eslint-disable-next-line
  }, [status]);

  return !monster ? null : (
    <>
      <>
        <p>{filteredRewards.length}</p>
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
        editStatus={editRewardStatus}
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

  async function editRewardStatus(taskId) {
    await editStatus(taskId);
    refresh();
  }

  function DisplayBalanceOrPayout() {
    if (status === 'OPEN') {
      return (
        <>
          <p>
            {monster ? monster.scoreDoneTasks - monster.payoutDoneRewards : ''}
          </p>
          <p>balance</p>
        </>
      );
    }
    return (
      <>
        <p>{monster?.payoutDoneRewards}</p>
        <p>payout</p>
      </>
    );
  }
}
