import React from 'react';
import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import TextareaField from '../components/TextareaField';
import InputField from '../components/InputField';

const initialState = {
  description: '',
  score: '',
};

export default function RewardForm({ onSave, reward = initialState }) {
  const [rewardData, setRewardData] = useState(reward);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <TextareaField
        itemType="reward"
        placeholder="enter reward"
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
      <button>Save</button>
      <button type="button" onClick={handleCancel}>
        Cancel
      </button>
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
    history.goBack();
  }
}
