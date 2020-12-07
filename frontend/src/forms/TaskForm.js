import React from 'react';
import { useHistory } from 'react-router-dom';
import { useState } from 'react';
import InputField from '../components/InputField';
import TextareaField from '../components/TextareaField';

const initialState = {
  description: '',
  score: '',
};

export default function TaskForm({ onSave, task = initialState }) {
  const [taskData, setTaskData] = useState(task);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <TextareaField
        itemType="task"
        placeholder="enter task"
        name="description"
        value={taskData.description}
        onChange={handleChange}
        type="text"
        required
      >
        task
      </TextareaField>
      <InputField
        itemType="task"
        placeholder="0"
        name="score"
        value={taskData.score}
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
    onSave(taskData.description, taskData.score);
  }

  function handleChange(event) {
    setTaskData({ ...taskData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.goBack();
  }
}
