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
        .then(() => history.push('/monsters'))
        .catch(console.log);
    }
  }, [code]);

  return (
    <>
      <title>Login</title>
      <p>Success</p>
    </>
  );
}
