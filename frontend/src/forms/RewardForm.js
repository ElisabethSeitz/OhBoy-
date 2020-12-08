import React from 'react';
import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import TextareaField from '../components/TextareaField';
import InputField from '../components/InputField';
import Button from '../components/Button';

const initialState = {
  description: '',
  score: '',
};

export default function RewardForm({
  onSave,
  reward = initialState,
  monsterId,
}) {
  const [rewardData, setRewardData] = useState(reward);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <TextareaField
        itemType="reward"
        name="description"
        value={rewardData.description}
        onChange={handleChange}
        type="text"
        required
      >
        reward
      </TextareaField>
      <InputField
        itemType="reward"
        placeholder="0"
        name="score"
        value={rewardData.score}
        onChange={handleChange}
        type="number"
        required
      >
        score
      </InputField>
      <Button name="save">Save</Button>
      <Button name="cancel" type="button" onClick={handleCancel}>
        Cancel
      </Button>
    </form>
  );

  function handleSubmit(event) {
    event.preventDefault();
    onSave(rewardData.description, rewardData.score);
  }

  function handleChange(event) {
    setRewardData({ ...rewardData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.push('/monsters/' + monsterId + '/rewards');
  }
}
