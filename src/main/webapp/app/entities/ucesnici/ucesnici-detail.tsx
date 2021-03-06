import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ucesnici.reducer';
import { IUcesnici } from 'app/shared/model/ucesnici.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUcesniciDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UcesniciDetail = (props: IUcesniciDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ucesniciEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="popraviApp.ucesnici.detail.title">Ucesnici</Translate> [<b>{ucesniciEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datum">
              <Translate contentKey="popraviApp.ucesnici.datum">Datum</Translate>
            </span>
          </dt>
          <dd>{ucesniciEntity.datum ? <TextFormat value={ucesniciEntity.datum} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="popraviApp.ucesnici.dodatniInfoUser">Dodatni Info User</Translate>
          </dt>
          <dd>
            {ucesniciEntity.dodatniInfoUsers
              ? ucesniciEntity.dodatniInfoUsers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ucesniciEntity.dodatniInfoUsers && i === ucesniciEntity.dodatniInfoUsers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/ucesnici" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ucesnici/${ucesniciEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ucesnici }: IRootState) => ({
  ucesniciEntity: ucesnici.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UcesniciDetail);
