import axios from 'axios';

const header = (token) => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

export const getRewardsByMonsterId = (token, monsterId) =>
  axios
    .get('/api/monster/' + monsterId + '/rewards', header(token))
    .then((response) => response.data);

export const addReward = (description, score, token, monsterId) =>
  axios
    .post(
      '/api/monster/' + monsterId + '/rewards',
      { description, score, monsterId },
      header(token)
    )
    .then((response) => response.data);

export const updateReward = (id, description, score, monsterId, token) =>
  axios
    .put(
      '/api/monster/' + monsterId + '/rewards/' + id,
      { id, description, score },
      header(token)
    )
    .then((response) => response.data);

export const updateStatus = (id, monsterId, token) =>
  axios
    .put(
      '/api/monster/' + monsterId + '/rewards/' + id + '/status',
      {},
      header(token)
    )
    .then((response) => response.data);

export const removeReward = (id, monsterId, token) =>
  axios
    .delete('/api/monster/' + monsterId + '/rewards/' + id, header(token))
    .then((response) => response.data);
