import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getTasksByMonsterId = (token, monsterId) =>
  axios
    .get('/api/monster/tasks/' + monsterId, header(token))
    .then((response) => response.data);
