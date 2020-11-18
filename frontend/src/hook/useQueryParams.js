import { useLocation } from 'react-router-dom';

export default function useQueryParams(...params) {
  const { search } = useLocation();
  const query = new URLSearchParams(search);
  return params.map((param) => query.get(param));
}
