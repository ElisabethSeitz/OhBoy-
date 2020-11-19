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

export const addMonster = (token, name, userId, image) =>
  axios
    .post('/api/monster', { name, userId, image }, header(token))
    .then((response) => response.data);
