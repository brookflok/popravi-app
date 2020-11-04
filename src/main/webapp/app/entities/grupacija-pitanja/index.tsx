import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GrupacijaPitanja from './grupacija-pitanja';
import GrupacijaPitanjaDetail from './grupacija-pitanja-detail';
import GrupacijaPitanjaUpdate from './grupacija-pitanja-update';
import GrupacijaPitanjaDeleteDialog from './grupacija-pitanja-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GrupacijaPitanjaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GrupacijaPitanjaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GrupacijaPitanjaDetail} />
      <ErrorBoundaryRoute path={match.url} component={GrupacijaPitanja} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GrupacijaPitanjaDeleteDialog} />
  </>
);

export default Routes;
