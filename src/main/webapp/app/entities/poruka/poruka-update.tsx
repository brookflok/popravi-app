import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDodatniInfoUser } from 'app/shared/model/dodatni-info-user.model';
import { getEntities as getDodatniInfoUsers } from 'app/entities/dodatni-info-user/dodatni-info-user.reducer';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';
import { getEntities as getGrupacijaPorukas } from 'app/entities/grupacija-poruka/grupacija-poruka.reducer';
import { getEntity, updateEntity, createEntity, reset } from './poruka.reducer';
import { IPoruka } from 'app/shared/model/poruka.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPorukaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PorukaUpdate = (props: IPorukaUpdateProps) => {
  const [dodatniinfouserId, setDodatniinfouserId] = useState('0');
  const [grupacijaPorukaId, setGrupacijaPorukaId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { porukaEntity, dodatniInfoUsers, grupacijaPorukas, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/poruka');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDodatniInfoUsers();
    props.getGrupacijaPorukas();
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
        ...porukaEntity,
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
          <h2 id="popraviApp.poruka.home.createOrEditLabel">
            <Translate contentKey="popraviApp.poruka.home.createOrEditLabel">Create or edit a Poruka</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : porukaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="poruka-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="poruka-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="textLabel" for="poruka-text">
                  <Translate contentKey="popraviApp.poruka.text">Text</Translate>
                </Label>
                <AvField id="poruka-text" type="text" name="text" />
              </AvGroup>
              <AvGroup>
                <Label id="datumLabel" for="poruka-datum">
                  <Translate contentKey="popraviApp.poruka.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="poruka-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.porukaEntity.datum)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="postojiLabel">
                  <AvInput id="poruka-postoji" type="checkbox" className="form-check-input" name="postoji" />
                  <Translate contentKey="popraviApp.poruka.postoji">Postoji</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="poruka-dodatniinfouser">
                  <Translate contentKey="popraviApp.poruka.dodatniinfouser">Dodatniinfouser</Translate>
                </Label>
                <AvInput id="poruka-dodatniinfouser" type="select" className="form-control" name="dodatniinfouser.id">
                  <option value="" key="0" />
                  {dodatniInfoUsers
                    ? dodatniInfoUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="poruka-grupacijaPoruka">
                  <Translate contentKey="popraviApp.poruka.grupacijaPoruka">Grupacija Poruka</Translate>
                </Label>
                <AvInput id="poruka-grupacijaPoruka" type="select" className="form-control" name="grupacijaPoruka.id">
                  <option value="" key="0" />
                  {grupacijaPorukas
                    ? grupacijaPorukas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/poruka" replace color="info">
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
  dodatniInfoUsers: storeState.dodatniInfoUser.entities,
  grupacijaPorukas: storeState.grupacijaPoruka.entities,
  porukaEntity: storeState.poruka.entity,
  loading: storeState.poruka.loading,
  updating: storeState.poruka.updating,
  updateSuccess: storeState.poruka.updateSuccess,
});

const mapDispatchToProps = {
  getDodatniInfoUsers,
  getGrupacijaPorukas,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PorukaUpdate);
