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
