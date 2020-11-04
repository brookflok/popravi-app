import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './slika.reducer';
import { ISlika } from 'app/shared/model/slika.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlikaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SlikaDetail = (props: ISlikaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { slikaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="popraviApp.slika.detail.title">Slika</Translate> [<b>{slikaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="ime">
              <Translate contentKey="popraviApp.slika.ime">Ime</Translate>
            </span>
          </dt>
          <dd>{slikaEntity.ime}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="popraviApp.slika.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {slikaEntity.image ? (
              <div>
                {slikaEntity.imageContentType ? (
                  <a onClick={openFile(slikaEntity.imageContentType, slikaEntity.image)}>
                    <img src={`data:${slikaEntity.imageContentType};base64,${slikaEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {slikaEntity.imageContentType}, {byteSize(slikaEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="uploaded">
              <Translate contentKey="popraviApp.slika.uploaded">Uploaded</Translate>
            </span>
          </dt>
          <dd>{slikaEntity.uploaded ? <TextFormat value={slikaEntity.uploaded} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="popraviApp.slika.mainslika">Mainslika</Translate>
          </dt>
          <dd>{slikaEntity.mainslika ? slikaEntity.mainslika.ime : ''}</dd>
          <dt>
            <Translate contentKey="popraviApp.slika.mainslika">Mainslika</Translate>
          </dt>
          <dd>{slikaEntity.mainslika ? slikaEntity.mainslika.ime : ''}</dd>
          <dt>
            <Translate contentKey="popraviApp.slika.galerija">Galerija</Translate>
          </dt>
          <dd>{slikaEntity.galerija ? slikaEntity.galerija.ime : ''}</dd>
        </dl>
        <Button tag={Link} to="/slika" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/slika/${slikaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ slika }: IRootState) => ({
  slikaEntity: slika.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SlikaDetail);