import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function LoginWithFacebook() {
  const [config, setConfig] = useState();

  useEffect(() => {
    axios
      .get('/auth/login/facebook')
      .then((response) => setConfig(response.data));
  }, []);

  if (!config) {
    return <div>loading</div>;
  }

  const state = '';
  const url = `https://www.facebook.com/v9.0/dialog/oauth?client_id=${config?.clientId}&redirect_uri=${config?.redirectUri}&state=${state}`;

  return <a href={url}>Login with Facebook</a>;
}
