import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from 'styled-components/macro';
import { SiFacebook } from 'react-icons/si';

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

  return (
    <>
      <Logo src="/OhKid!_withoutBackground.png" alt="Logo OhKid!" />
      <FacebookButton>
        <SiFacebookStyled />
        <a className="facebookLink" href={url}>
          Login with Facebook
        </a>
      </FacebookButton>
    </>
  );
}

const Logo = styled.img`
  width: 350px;
  height: 350px;
  justify-self: center;
`;

const FacebookButton = styled.button`
  display: grid;
  grid-template-columns: min-content 1fr;
  grid-template-rows: 1fr;
  color: white;
  border-radius: var(--size-s);
  width: 225px;
  height: 40px;
  background-color: rgb(68, 120, 242);
  border: none;
  align-items: center;
  justify-items: center;
  justify-self: center;
  align-self: center;

  .facebookLink {
    color: white;
    text-decoration: none;
    font-weight: bold;
    font-size: var(--size-l);
    grid-column: 2;
  }
`;

const SiFacebookStyled = styled(SiFacebook)`
  font-size: var(--size-xl);
  grid-column: 1;
  margin-left: var(--size-s);
`;
