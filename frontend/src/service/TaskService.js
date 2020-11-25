import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getTasksByMonsterId = (token, monsterId) =>
  axios
    .get('/api/monster/' + monsterId + '/tasks', header(token))
    .then((response) => response.data);

export const addTask = (description, score, token, monsterId) =>
  axios
    .post(
      '/api/monster/' + monsterId + '/tasks',
      { description, score, monsterId },
      header(token)
    )
    .then((response) => response.data);
