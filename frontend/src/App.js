import React from 'react';
import LoginWithFacebook from './facebookLogin/LoginWithFacebook';
import UserContextProvider from './contexts/UserContextProvider';
import FacebookRedirectPage from './oauthPage/FacebookRedirectPage';
import { Route, Switch, Redirect } from 'react-router-dom';
import MonsterPage from './MonsterPage';
import ProtectedRoute from './routing/ProtectedRoute';

function App() {
  return (
    <UserContextProvider>
      <Switch>
        <Route path="/login" component={LoginWithFacebook} />
        <Route
          path="/auth/facebook/redirect"
          component={FacebookRedirectPage}
        />
        <ProtectedRoute path="/monsters" component={MonsterPage} />
        <Route path="/">
          <Redirect to="/monsters" />
        </Route>
      </Switch>
    </UserContextProvider>
  );
}

export default App;
