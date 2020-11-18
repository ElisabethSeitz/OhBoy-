import React, { useContext, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import UserContext from '../contexts/UserContext';
import useQueryParams from '../hook/useQueryParams';

export default function FacebookRedirectPage() {
  const { loginWithFacebookCode } = useContext(UserContext);
  const [code] = useQueryParams('code');
  const history = useHistory();

  useEffect(() => {
    if (code) {
      loginWithFacebookCode(code)
        .then(() => history.push('/'))
        .catch(console.log);
    }
    // eslint-disable-next-line
  }, [code]);

  return (
    <>
      <p>Success, welcome to OhBoy</p>
    </>
  );
}
