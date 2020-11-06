import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Kategorija from './kategorija';
import KategorijaDetail from './kategorija-detail';
import KategorijaUpdate from './kategorija-update';
import KategorijaDeleteDialog from './kategorija-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KategorijaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KategorijaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KategorijaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Kategorija} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KategorijaDeleteDialog} />
  </>
);

export default Routes;
