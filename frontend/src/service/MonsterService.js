import axios from 'axios';

export const setup = () =>
  axios.get('/api/monster').then((response) => response.data);
