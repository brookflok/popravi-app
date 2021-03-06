import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './javno-pitanje.reducer';
import { IJavnoPitanje } from 'app/shared/model/javno-pitanje.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IJavnoPitanjeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const JavnoPitanje = (props: IJavnoPitanjeProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { javnoPitanjeList, match, loading } = props;
  return (
    <div>
      <h2 id="javno-pitanje-heading">
        <Translate contentKey="popraviApp.javnoPitanje.home.title">Javno Pitanjes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="popraviApp.javnoPitanje.home.createLabel">Create new Javno Pitanje</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('popraviApp.javnoPitanje.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {javnoPitanjeList && javnoPitanjeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.pitanje">Pitanje</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.datum">Datum</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.prikaz">Prikaz</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.odgovorNaJavnoPitanje">Odgovor Na Javno Pitanje</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.dodatniinfoUser">Dodatniinfo User</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.javnoPitanje.grupacijaPitanja">Grupacija Pitanja</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {javnoPitanjeList.map((javnoPitanje, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${javnoPitanje.id}`} color="link" size="sm">
                      {javnoPitanje.id}
                    </Button>
                  </td>
                  <td>{javnoPitanje.pitanje}</td>
                  <td>{javnoPitanje.datum ? <TextFormat type="date" value={javnoPitanje.datum} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{javnoPitanje.prikaz ? 'true' : 'false'}</td>
                  <td>
                    {javnoPitanje.odgovorNaJavnoPitanje ? (
                      <Link to={`odgovor-na-javno-pitanje/${javnoPitanje.odgovorNaJavnoPitanje.id}`}>
                        {javnoPitanje.odgovorNaJavnoPitanje.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {javnoPitanje.dodatniinfoUser ? (
                      <Link to={`dodatni-info-user/${javnoPitanje.dodatniinfoUser.id}`}>{javnoPitanje.dodatniinfoUser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {javnoPitanje.grupacijaPitanja ? (
                      <Link to={`grupacija-pitanja/${javnoPitanje.grupacijaPitanja.id}`}>{javnoPitanje.grupacijaPitanja.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${javnoPitanje.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${javnoPitanje.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${javnoPitanje.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="popraviApp.javnoPitanje.home.notFound">No Javno Pitanjes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ javnoPitanje }: IRootState) => ({
  javnoPitanjeList: javnoPitanje.entities,
  loading: javnoPitanje.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JavnoPitanje);
