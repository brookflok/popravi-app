import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IChat } from 'app/shared/model/chat.model';
import { getEntities as getChats } from 'app/entities/chat/chat.reducer';
import { getEntity, updateEntity, createEntity, reset } from './grupacija-poruka.reducer';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGrupacijaPorukaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GrupacijaPorukaUpdate = (props: IGrupacijaPorukaUpdateProps) => {
  const [chatId, setChatId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { grupacijaPorukaEntity, chats, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/grupacija-poruka');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getChats();
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
        ...grupacijaPorukaEntity,
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
          <h2 id="popraviApp.grupacijaPoruka.home.createOrEditLabel">
            <Translate contentKey="popraviApp.grupacijaPoruka.home.createOrEditLabel">Create or edit a GrupacijaPoruka</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : grupacijaPorukaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="grupacija-poruka-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="grupacija-poruka-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="datumLabel" for="grupacija-poruka-datum">
                  <Translate contentKey="popraviApp.grupacijaPoruka.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="grupacija-poruka-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.grupacijaPorukaEntity.datum)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/grupacija-poruka" replace color="info">
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
  chats: storeState.chat.entities,
  grupacijaPorukaEntity: storeState.grupacijaPoruka.entity,
  loading: storeState.grupacijaPoruka.loading,
  updating: storeState.grupacijaPoruka.updating,
  updateSuccess: storeState.grupacijaPoruka.updateSuccess,
});

const mapDispatchToProps = {
  getChats,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GrupacijaPorukaUpdate);
