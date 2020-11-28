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
import AddTaskPage from './pages/AddTaskPage';
import EditTaskPage from './pages/EditTaskPage';
import RewardPage from './pages/RewardPage';
import AddRewardPage from './pages/AddRewardPage';
import EditRewardPage from './pages/EditRewardPage';

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
            exact
            path="/monsters/:monsterId/tasks"
            component={TaskPage}
          />
          <ProtectedRoute
            path="/monsters/:monsterId/tasks/create"
            component={AddTaskPage}
          />
          <ProtectedRoute
            path="/monsters/:monsterId/tasks/edit/:taskId"
            component={EditTaskPage}
          />
          <ProtectedRoute
            exact
            path="/monsters/:monsterId/rewards"
            component={RewardPage}
          />
          <ProtectedRoute
            path="/monsters/:monsterId/rewards/create"
            component={AddRewardPage}
          />
          <ProtectedRoute
            path="/monsters/:monsterId/rewards/edit/:rewardId"
            component={EditRewardPage}
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
