import { useParams } from 'react-router-dom';
import React, { useContext, useEffect, useState } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardList from '../lists/RewardList';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSection from '../components/MonsterSection';
import OpenDoneButtons from '../components/OpenDoneButtons';

export default function RewardPage() {
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
  }, [monsters, monsterId]);

  useEffect(() => {
    rewardsFilter(status, false).then(setFilteredRewards);
    // eslint-disable-next-line
  }, [status]);

  return !monster ? null : (
    <>
      <Header
        currentMonsterId={monsterId}
        task={false}
        icons={true}
        add={true}
      />
      <MonsterSection
        monster={monster}
        filteredItems={filteredRewards}
        status={status}
        task={false}
      />
      <OpenDoneButtons
        handleOnClickDONE={handleOnClickDONE}
        handleOnClickOPEN={handleOnClickOPEN}
      />
      <RewardList
        rewards={filteredRewards}
        monsterId={monsterId}
        editStatus={editRewardStatus}
      />
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
}
