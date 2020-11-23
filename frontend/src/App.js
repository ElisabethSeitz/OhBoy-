import React from 'react';
import LoginWithFacebook from './facebookLogin/LoginWithFacebook';
import UserContextProvider from './contexts/UserContextProvider';
import FacebookRedirectPage from './pages/FacebookRedirectPage';
import { Route, Switch, Redirect } from 'react-router-dom';
import ProtectedRoute from './routing/ProtectedRoute';
import MonsterPage from './pages/MonsterPage';
import MonsterContextProvider from './contexts/MonsterContextProvider';
import AddMonsterPage from './pages/AddMonsterPage';
import EditMonsterPage from './pages/EditMonsterPage';
import TaskPage from './pages/TaskPage';
import TaskContextProvider from './contexts/TaskContextProvider';

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
          <ProtectedRoute exact path="/monsters" component={MonsterPage} />
          <ProtectedRoute path="/monsters/create" component={AddMonsterPage} />
          <ProtectedRoute
            path="/monsters/edit/:id"
            component={EditMonsterPage}
          />

          <ProtectedRoute
            path="/monsters/tasks/:monsterId"
            component={TaskPage}
          />

          <Route path="/">
            <Redirect to="/monsters" />
          </Route>
        </Switch>
      </MonsterContextProvider>
    </UserContextProvider>
  );
}

export default App;
