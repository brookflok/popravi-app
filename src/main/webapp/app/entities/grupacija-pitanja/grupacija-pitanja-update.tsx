import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArtikl } from 'app/shared/model/artikl.model';
import { getEntities as getArtikls } from 'app/entities/artikl/artikl.reducer';
import { getEntity, updateEntity, createEntity, reset } from './grupacija-pitanja.reducer';
import { IGrupacijaPitanja } from 'app/shared/model/grupacija-pitanja.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGrupacijaPitanjaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupacijaPitanjaUpdate = (props: IGrupacijaPitanjaUpdateProps) => {
  const [artiklId, setArtiklId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { grupacijaPitanjaEntity, artikls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/grupacija-pitanja');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getArtikls();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.datum = convertDateTimeToServer(values.datum);

    if (errors.length === 0) {
      const entity = {
        ...grupacijaPitanjaEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="popraviApp.grupacijaPitanja.home.createOrEditLabel">
            <Translate contentKey="popraviApp.grupacijaPitanja.home.createOrEditLabel">Create or edit a GrupacijaPitanja</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : grupacijaPitanjaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="grupacija-pitanja-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="grupacija-pitanja-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="datumLabel" for="grupacija-pitanja-datum">
                  <Translate contentKey="popraviApp.grupacijaPitanja.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="grupacija-pitanja-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.grupacijaPitanjaEntity.datum)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/grupacija-pitanja" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  artikls: storeState.artikl.entities,
  grupacijaPitanjaEntity: storeState.grupacijaPitanja.entity,
  loading: storeState.grupacijaPitanja.loading,
  updating: storeState.grupacijaPitanja.updating,
  updateSuccess: storeState.grupacijaPitanja.updateSuccess,
});

const mapDispatchToProps = {
  getArtikls,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupacijaPitanjaUpdate);
