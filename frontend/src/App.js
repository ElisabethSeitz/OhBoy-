import React from 'react';
import LoginWithFacebook from './facebookLogin/LoginWithFacebook';
import UserContextProvider from './contexts/UserContextProvider';
import FacebookRedirectPage from './pages/FacebookRedirectPage';
import { Route, Switch, Redirect } from 'react-router-dom';
import ProtectedRoute from './routing/ProtectedRoute';
import MonsterPage from './pages/MonsterPage';
import MonsterContextProvider from './contexts/MonsterContextProvider';

function App() {
  return (
    <UserContextProvider>
      <MonsterContextProvider>
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
      </MonsterContextProvider>
    </UserContextProvider>
  );
}

export default App;
