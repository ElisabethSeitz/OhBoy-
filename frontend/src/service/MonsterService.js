import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getMonsters = (token) =>
  axios.get('/api/monster', header(token)).then((response) => response.data);
