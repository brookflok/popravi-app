import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GrupacijaPoruka from './grupacija-poruka';
import GrupacijaPorukaDetail from './grupacija-poruka-detail';
import GrupacijaPorukaUpdate from './grupacija-poruka-update';
import GrupacijaPorukaDeleteDialog from './grupacija-poruka-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GrupacijaPorukaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GrupacijaPorukaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GrupacijaPorukaDetail} />
      <ErrorBoundaryRoute path={match.url} component={GrupacijaPoruka} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GrupacijaPorukaDeleteDialog} />
  </>
);

export default Routes;
