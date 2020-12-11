import React, { useEffect, useState } from 'react';
import UserContext from './UserContext';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import {
  loadTokenFromLocalStorage,
  loadUserDataFromLocalStorage,
  saveTokenToLocalStorage,
  saveUserDataToLocalStorage,
  removeTokenFromLocalStorage,
  removeUserDataFromLocalStorage,
} from '../service/LocalStorage';

export default function UserContextProvider({ children }) {
  const [token, setToken] = useState(loadTokenFromLocalStorage());
  const [userData, setUserData] = useState(loadUserDataFromLocalStorage());

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);
        if (decoded.exp > new Date().getTime() / 1000) {
          setUserData(decoded);
          saveTokenToLocalStorage(token);
          saveUserDataToLocalStorage(decoded);
        }
      } catch (e) {
        console.log(e);
      }
    }
  }, [token]);

  const tokenIsValid = () =>
    token && userData?.exp > new Date().getTime() / 1000;

  const loginWithFacebookCode = (code) =>
    axios
      .post('/auth/login/facebook', { code })
      .then((response) => setToken(response.data));

  const logoutFacebook = () =>
    axios
      .delete('/auth/login/facebook/logout', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(removeTokenFromLocalStorage)
      .then(removeUserDataFromLocalStorage);

  return (
    <UserContext.Provider
      value={{
        token,
        tokenIsValid,
        loginWithFacebookCode,
        userData,
        logoutFacebook,
      }}
    >
      {children}
    </UserContext.Provider>
  );
}
