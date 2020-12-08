import { useParams } from 'react-router-dom';
import React, { useContext, useEffect, useState } from 'react';
import useRewardsByMonsterId from '../hook/useRewardsByMonsterId';
import RewardList from '../lists/RewardList';
import MonsterContext from '../contexts/MonsterContext';
import Header from '../components/Header';
import MonsterSection from '../components/MonsterSection';
import OpenDoneSwitch from '../components/OpenDoneSwitch';
import AddButton from '../components/AddButton';

export default function RewardPage() {
  const { monsterId } = useParams();
  const { refresh, monsters } = useContext(MonsterContext);
  const [status, setStatus] = useState('OPEN');

  const [monster, setMonster] = useState();
  const { filterRewards, editStatus } = useRewardsByMonsterId(monsterId);
  const [filteredRewards, setFilteredRewards] = useState([]);

  useEffect(() => {
    setMonster(monsters.find((m) => m.id === monsterId));
    filterRewards(status, true).then(setFilteredRewards);
    // eslint-disable-next-line
  }, [monsters, monsterId]);

  useEffect(() => {
    filterRewards(status, false).then(setFilteredRewards);
    // eslint-disable-next-line
  }, [status]);

  return !monster ? null : (
    <>
      <Header displayedMonsterId={monsterId} itemType="reward" icons={true} />
      <div>
        <MonsterSection
          monster={monster}
          filteredItems={filteredRewards}
          status={status}
          itemType="reward"
        />
        <OpenDoneSwitch
          handleOnClickDONE={handleOnClickDONE}
          handleOnClickOPEN={handleOnClickOPEN}
          itemType="reward"
        />
      </div>
      <RewardList
        rewards={filteredRewards}
        monsterId={monsterId}
        editStatus={editRewardStatus}
      />
      <AddButton
        monster={false}
        itemType="reward"
        currentMonsterId={monsterId}
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
