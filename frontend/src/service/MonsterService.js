import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getMonstersByUserId = (token) =>
  axios.get('/api/monster/', header(token)).then((response) => response.data);

export const addMonster = (token, name, image) =>
  axios
    .post('/api/monster', { name, image }, header(token))
    .then((response) => response.data);

export const updateMonster = (id, name, image, token) =>
  axios
    .put('/api/monster/' + id, { id, name, image }, header(token))
    .then((response) => response.data);

export const removeMonster = (id, token) =>
  axios
    .delete('/api/monster/' + id, header(token))
    .then((response) => response.data);
