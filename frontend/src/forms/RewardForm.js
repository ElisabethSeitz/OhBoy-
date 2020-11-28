import React from 'react';
import { useState } from 'react';
import { useHistory } from 'react-router-dom';

const initialState = {
  description: '',
  score: '',
};

export default function RewardForm({ onSave, reward = initialState }) {
  const [rewardData, setRewardData] = useState(reward);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <label>
        reward
        <input
          name="description"
          value={rewardData.description}
          onChange={handleChange}
          type="text"
          required
        />
      </label>
      <label>
        score
        <input
          name="score"
          value={rewardData.score}
          onChange={handleChange}
          type="number"
          required
        />
      </label>
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
