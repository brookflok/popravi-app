import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './grupacija-pitanja.reducer';
import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupacijaPitanjaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupacijaPitanjaDetail = (props: IGrupacijaPitanjaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { grupacijaPitanjaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="popraviApp.grupacijaPitanja.detail.title">GrupacijaPitanja</Translate> [<b>{grupacijaPitanjaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datum">
              <Translate contentKey="popraviApp.grupacijaPitanja.datum">Datum</Translate>
            </span>
          </dt>
          <dd>
            {grupacijaPitanjaEntity.datum ? <TextFormat value={grupacijaPitanjaEntity.datum} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/grupacija-pitanja" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupacija-pitanja/${grupacijaPitanjaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ grupacijaPitanja }: IRootState) => ({
  grupacijaPitanjaEntity: grupacijaPitanja.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupacijaPitanjaDetail);
