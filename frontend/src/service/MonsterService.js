import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getMonstersByUserId = (token, userId) =>
  axios
    .get('/api/monster/' + userId, header(token))
    .then((response) => response.data);
