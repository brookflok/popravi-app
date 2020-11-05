import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUcesnici } from 'app/shared/model/ucesnici.model';
import { getEntities as getUcesnicis } from 'app/entities/ucesnici/ucesnici.reducer';
import { IGrupacijaPoruka } from 'app/shared/model/grupacija-poruka.model';
import { getEntities as getGrupacijaPorukas } from 'app/entities/grupacija-poruka/grupacija-poruka.reducer';
import { IArtikl } from 'app/shared/model/artikl.model';
import { getEntities as getArtikls } from 'app/entities/artikl/artikl.reducer';
import { getEntity, updateEntity, createEntity, reset } from './chat.reducer';
import { IChat } from 'app/shared/model/chat.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChatUpdate = (props: IChatUpdateProps) => {
  const [ucesniciId, setUcesniciId] = useState('0');
  const [grupacijaPorukaId, setGrupacijaPorukaId] = useState('0');
  const [artiklId, setArtiklId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { chatEntity, ucesnicis, grupacijaPorukas, artikls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/chat');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUcesnicis();
    props.getGrupacijaPorukas();
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
        ...chatEntity,
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
          <h2 id="popraviApp.chat.home.createOrEditLabel">
            <Translate contentKey="popraviApp.chat.home.createOrEditLabel">Create or edit a Chat</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : chatEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="chat-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="chat-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="datumLabel" for="chat-datum">
                  <Translate contentKey="popraviApp.chat.datum">Datum</Translate>
                </Label>
                <AvInput
                  id="chat-datum"
                  type="datetime-local"
                  className="form-control"
                  name="datum"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.chatEntity.datum)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="postojiLabel">
                  <AvInput id="chat-postoji" type="checkbox" className="form-check-input" name="postoji" />
                  <Translate contentKey="popraviApp.chat.postoji">Postoji</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="chat-ucesnici">
                  <Translate contentKey="popraviApp.chat.ucesnici">Ucesnici</Translate>
                </Label>
                <AvInput id="chat-ucesnici" type="select" className="form-control" name="ucesnici.id">
                  <option value="" key="0" />
                  {ucesnicis
                    ? ucesnicis.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="chat-grupacijaPoruka">
                  <Translate contentKey="popraviApp.chat.grupacijaPoruka">Grupacija Poruka</Translate>
                </Label>
                <AvInput id="chat-grupacijaPoruka" type="select" className="form-control" name="grupacijaPoruka.id">
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
              <AvGroup>
                <Label for="chat-artikl">
                  <Translate contentKey="popraviApp.chat.artikl">Artikl</Translate>
                </Label>
                <AvInput id="chat-artikl" type="select" className="form-control" name="artikl.id">
                  <option value="" key="0" />
                  {artikls
                    ? artikls.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/chat" replace color="info">
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
  ucesnicis: storeState.ucesnici.entities,
  grupacijaPorukas: storeState.grupacijaPoruka.entities,
  artikls: storeState.artikl.entities,
  chatEntity: storeState.chat.entity,
  loading: storeState.chat.loading,
  updating: storeState.chat.updating,
  updateSuccess: storeState.chat.updateSuccess,
});

const mapDispatchToProps = {
  getUcesnicis,
  getGrupacijaPorukas,
  getArtikls,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChatUpdate);
