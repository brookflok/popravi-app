import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './grupacija-poruka.reducer';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGrupacijaPorukaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupacijaPorukaDetail = (props: IGrupacijaPorukaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { grupacijaPorukaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="popraviApp.grupacijaPoruka.detail.title">GrupacijaPoruka</Translate> [<b>{grupacijaPorukaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="datum">
              <Translate contentKey="popraviApp.grupacijaPoruka.datum">Datum</Translate>
            </span>
          </dt>
          <dd>
            {grupacijaPorukaEntity.datum ? <TextFormat value={grupacijaPorukaEntity.datum} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/grupacija-poruka" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupacija-poruka/${grupacijaPorukaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ grupacijaPoruka }: IRootState) => ({
  grupacijaPorukaEntity: grupacijaPoruka.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupacijaPorukaDetail);
