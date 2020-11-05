import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './artikl.reducer';
import { IArtikl } from 'app/shared/model/artikl.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArtiklProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Artikl = (props: IArtiklProps) => {
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

  const { artiklList, match, loading } = props;
  return (
    <div>
      <h2 id="artikl-heading">
        <Translate contentKey="popraviApp.artikl.home.title">Artikls</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="popraviApp.artikl.home.createLabel">Create new Artikl</Translate>
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
                  placeholder={translate('popraviApp.artikl.home.search')}
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
        {artiklList && artiklList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.ime">Ime</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.kratkiOpis">Kratki Opis</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.detaljniOpis">Detaljni Opis</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.majstor">Majstor</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.postoji">Postoji</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.lokacija">Lokacija</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.potreba">Potreba</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.usluga">Usluga</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.galerija">Galerija</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.mainSlika">Main Slika</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.informacije">Informacije</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.grupacijaPitanja">Grupacija Pitanja</Translate>
                </th>
                <th>
                  <Translate contentKey="popraviApp.artikl.dodatniinfouser">Dodatniinfouser</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {artiklList.map((artikl, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${artikl.id}`} color="link" size="sm">
                      {artikl.id}
                    </Button>
                  </td>
                  <td>{artikl.ime}</td>
                  <td>{artikl.kratkiOpis}</td>
                  <td>{artikl.detaljniOpis}</td>
                  <td>{artikl.majstor ? 'true' : 'false'}</td>
                  <td>{artikl.postoji ? 'true' : 'false'}</td>
                  <td>{artikl.lokacija ? <Link to={`lokacija/${artikl.lokacija.id}`}>{artikl.lokacija.id}</Link> : ''}</td>
                  <td>{artikl.potreba ? <Link to={`potreba/${artikl.potreba.id}`}>{artikl.potreba.id}</Link> : ''}</td>
                  <td>{artikl.usluga ? <Link to={`usluga/${artikl.usluga.id}`}>{artikl.usluga.id}</Link> : ''}</td>
                  <td>{artikl.galerija ? <Link to={`galerija/${artikl.galerija.id}`}>{artikl.galerija.id}</Link> : ''}</td>
                  <td>{artikl.mainSlika ? <Link to={`main-slika/${artikl.mainSlika.id}`}>{artikl.mainSlika.id}</Link> : ''}</td>
                  <td>{artikl.informacije ? <Link to={`informacije/${artikl.informacije.id}`}>{artikl.informacije.id}</Link> : ''}</td>
                  <td>
                    {artikl.grupacijaPitanja ? (
                      <Link to={`grupacija-pitanja/${artikl.grupacijaPitanja.id}`}>{artikl.grupacijaPitanja.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {artikl.dodatniinfouser ? (
                      <Link to={`dodatni-info-user/${artikl.dodatniinfouser.id}`}>{artikl.dodatniinfouser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${artikl.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${artikl.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${artikl.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="popraviApp.artikl.home.notFound">No Artikls found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ artikl }: IRootState) => ({
  artiklList: artikl.entities,
  loading: artikl.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Artikl);
