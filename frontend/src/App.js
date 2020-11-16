import React from 'react';
import LoginWithFacebook from './facebookLogin/LoginWithFacebook';
import UserContextProvider from './contexts/UserContextProvider';
import FacebookRedirectPage from './oauthPage/FacebookRedirectPage';
import { Route, Switch, Redirect } from 'react-router-dom';
import MonsterPage from './MonsterPage';

function App() {
  return (
    <UserContextProvider>
      <Switch>
        <Route
          path="/auth/facebook/redirect"
          component={FacebookRedirectPage}
        />
        <Route path="/login" component={LoginWithFacebook} />
        <Route path="/monsters" component={MonsterPage} />
        <Route path="/">
          <Redirect to="/login" />
        </Route>
      </Switch>
    </UserContextProvider>
  );
}

export default App;
