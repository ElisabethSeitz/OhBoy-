import axios from 'axios';

export const setup =
  /**
   * @returns {Promise<string>}
   */
  () => axios.get('/api/monster').then((response) => response.data);
