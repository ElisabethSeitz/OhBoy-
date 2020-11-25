import { useHistory } from 'react-router-dom';
import { useState } from 'react';

const initialState = {
  description: '',
  score: '',
};

export default function TaskForm({ onSave, task = initialState }) {
  const [taskData, setTaskData] = useState(task);
  const history = useHistory();

  return (
    <form onSubmit={handleSubmit}>
      <label>
        task
        <input
          name="description"
          value={taskData.description}
          onChange={handleChange}
          type="text"
          required
        />
      </label>
      <label>
        score
        <input
          name="score"
          value={taskData.score}
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
    onSave(taskData.description, taskData.score);
  }

  function handleChange(event) {
    setTaskData({ ...taskData, [event.target.name]: event.target.value });
  }

  function handleCancel() {
    history.goBack();
  }
}
