import React, { useState } from 'react';
import FacebookLogin from 'react-facebook-login';

export default function LoginWithFacebook() {
  const [login, setLogin] = useState(false);
  const [data, setData] = useState({});

  const responseFacebook = (response) => {
    console.log(response);
    setData(response);
    if (response.accessToken) {
      setLogin(true);
    } else {
      setLogin(false);
    }
  };

  return (
    <>
      <div>
        {!login && (
          <FacebookLogin
            appId="403563560686849"
            autoLoad={true}
            fields="name"
            scope="public_profile"
            callback={responseFacebook}
            icon="fa-facebook"
          />
        )}
        {login && data.name}
      </div>
    </>
  );
}
